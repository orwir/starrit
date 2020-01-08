package orwir.starrit.authorization.internal

import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.authorization.AccessRepository
import orwir.starrit.authorization.AuthorizationFlowRepository
import orwir.starrit.authorization.BuildConfig.CLIENT_ID
import orwir.starrit.authorization.BuildConfig.REDIRECT_URI
import orwir.starrit.authorization.TokenException
import orwir.starrit.authorization.model.*
import orwir.starrit.core.extension.KoinedShareable
import orwir.starrit.core.extension.Shareable
import orwir.starrit.core.extension.enumPref
import orwir.starrit.core.extension.objPref
import java.util.*

internal class BasicAccessRepository :
    AccessRepository,
    AuthorizationFlowRepository,
    TokenRepository,
    KoinComponent,
    Shareable by KoinedShareable(),
    CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private var requestState: String? by objPref()
    private var requestCallback: Callback? = null
    private var responseUri: Uri? = null
    private var token: Token? by objPref()
    private var accessType: AccessType by enumPref()

    private val service: AuthorizationService by inject()
    private val scope = listOf(Scope.Identity, Scope.Read).joinToString { it.asParameter() }

    override suspend fun accessType(): AccessType =
        when (accessType) {
            AccessType.Authorized ->
                try {
                    obtainToken()
                    AccessType.Authorized
                } catch (e: Exception) {
                    accessType = AccessType.Unspecified
                    accessType
                }
            else -> accessType
        }

    override suspend fun obtainToken(): Token =
        token?.let {
            if (it.isExpired()) {
                try {
                    token = service.refreshToken(it.refresh).copy(refresh = it.refresh)
                } catch (e: Exception) {
                    throw TokenException("Token refresh failed!", e)
                }
            }
            token
        } ?: throw TokenException("Token not found!")

    @ExperimentalCoroutinesApi
    override fun flow(): Flow<Step> = callbackFlow {
        if (requestCallback == null) {
            requestCallback = object : Callback {
                override fun onStart() {
                    requestState = requestState ?: UUID.randomUUID().toString()
                    responseUri = null
                    offer(Step.Start(buildAuthorizationUri(requestState!!, scope)))
                }

                override fun onAnonymous() {
                    token = null
                    accessType = AccessType.Anonymous
                    onSuccess()
                }

                override fun onSuccess() {
                    requestState = null
                    responseUri = null
                    offer(Step.Success)
                    channel.close()
                }

                override fun onError(exception: Exception) {
                    requestState = null
                    responseUri = null
                    offer(Step.Failure(exception))
                }

                override fun onReset() {
                    requestState = null
                    responseUri = null
                    offer(Step.Idle)
                }
            }
        }

        if (responseUri == null) {
            offer(Step.Idle)
        } else {
            completeFlow(responseUri!!)
        }

        awaitClose {
            requestCallback = null
        }
    }

    override fun startFlow() {
        requestCallback?.onStart()
    }

    override fun completeFlow(response: Uri) {
        if (requestCallback == null) {
            responseUri = response
            return
        }
        if (requestState == null) {
            return
        }

        val callback = requestCallback!!
        val expectedState = requestState!!
        val actualState = response.getQueryParameter("state")
        val error = response.getQueryParameter("error")
            ?.toUpperCase(Locale.ENGLISH)
            ?.let(TokenException.ErrorCode::valueOf)

        if (expectedState != actualState) {
            callback.onError(TokenException("Request/Response state mismatch: e:[$expectedState], a:[$actualState]"))
            return
        }
        if (error != null) {
            callback.onError(TokenException("API Error: $error", code = error))
            return
        }

        launch {
            try {
                val code = response.getQueryParameter("code")!!
                token = service.accessToken(code)
                accessType = AccessType.Authorized
                callback.onSuccess()
            } catch (e: Exception) {
                callback.onError(e)
            }
        }
    }

    override fun resetFlow() {
        requestCallback?.onReset()
    }

    override fun anonymousAccess() {
        requestCallback?.onAnonymous()
    }

}

private interface Callback {
    fun onStart()
    fun onAnonymous()
    fun onSuccess()
    fun onError(exception: Exception)
    fun onReset()
}

private const val ADDITIONAL_THRESHOLD_S = 5

private fun Token.isExpired() =
    System.currentTimeMillis() / 1000 >= (obtained + expires - ADDITIONAL_THRESHOLD_S)

private fun buildAuthorizationUri(state: String, scope: String): Uri =
    Uri.Builder()
        .apply {
            scheme("https")
            authority("www.reddit.com")
            path("api/v1/authorize.compact")
            appendQueryParameter("client_id", CLIENT_ID)
            appendQueryParameter("response_type", "code")
            appendQueryParameter("state", state)
            appendQueryParameter("redirect_uri", REDIRECT_URI)
            appendQueryParameter("duration", Duration.Permanent.asParameter())
            appendQueryParameter("scope", scope)
        }
        .build()