package orwir.starrit.authorization.internal

import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.authorization.AccessRepository
import orwir.starrit.authorization.AuthorizationRepository
import orwir.starrit.authorization.BuildConfig.CLIENT_ID
import orwir.starrit.authorization.BuildConfig.REDIRECT_URI
import orwir.starrit.authorization.TokenException
import orwir.starrit.authorization.model.*
import orwir.starrit.core.extension.InjectedShareable
import orwir.starrit.core.extension.Shareable
import orwir.starrit.core.extension.enumPref
import orwir.starrit.core.extension.objPref
import java.util.*

internal class BaseAccessRepository :
    AccessRepository,
    AuthorizationRepository,
    TokenRepository,
    KoinComponent,
    Shareable by InjectedShareable(),
    CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private var requestState: String? by objPref()
    private var requestCallback: Callback? = null
    private var responseUri: Uri? = null
    private var token: Token? by objPref()
    private var access: AccessType by enumPref()

    private val service: AuthorizationService by inject()
    private val scope = listOf(Scope.Identity, Scope.Read).joinToString { it.asParameter() }

    override suspend fun type(): AccessType =
        when (access) {
            AccessType.Authorized ->
                try {
                    obtainToken()
                    AccessType.Authorized
                } catch (e: Exception) {
                    AccessType.Revoked
                }
            else -> access
        }

    override suspend fun obtainToken(): Token =
        token?.let {
            if (it.isExpired()) {
                try {
                    token = service.refreshToken(it.refresh).copy(refresh = it.refresh)
                } catch (e: Exception) {
                    throw e.takeIf { e is TokenException } ?: TokenException("Token refresh failed!", e)
                }
            }
            token
        } ?: throw TokenException("Token not found!")

    @ExperimentalCoroutinesApi
    override fun flow(): Flow<Step> = callbackFlow {
        requestCallback = requestCallback ?: createRequestCallback()

        if (isRestored()) {
            completeFlow(responseUri!!)
        } else {
            send(Step.Idle)
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
        if (expectedState != actualState) {
            callback.onError(TokenException("Request/Response state mismatch: e:[$expectedState], a:[$actualState]"))
            return
        }

        val errorCode = response.getQueryParameter("error")
            ?.toUpperCase(Locale.ENGLISH)
            ?.let(TokenException.ErrorCode::valueOf)
        if (errorCode != null) {
            callback.onError(TokenException("OAuth error: $errorCode", code = errorCode))
            return
        }

        launch {
            try {
                val code = response.getQueryParameter("code")!!
                token = service.obtainToken(code)
                access = AccessType.Authorized
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
        access = AccessType.Anonymous
        token = null
        requestCallback?.onAnonymous()
    }

    private fun isRestored(): Boolean = responseUri != null

    @ExperimentalCoroutinesApi
    private fun SendChannel<Step>.createRequestCallback(): Callback =
        object : Callback {

            override fun onStart() {
                launch {
                    onCall(requestState ?: UUID.randomUUID().toString())
                    send(Step.Start(buildAuthorizationUri(requestState!!, scope)))
                }
            }

            override fun onAnonymous() {
                onSuccess()
            }

            override fun onSuccess() {
                launch {
                    onCall(null)
                    send(Step.Success)
                    close()
                }
            }

            override fun onError(exception: Exception) {
                launch {
                    onCall(null)
                    send(Step.Failure(exception))
                }
            }

            override fun onReset() {
                launch {
                    onCall(null)
                    send(Step.Idle)
                }
            }

            private fun onCall(state: String?) {
                requestState = state
                responseUri = null
            }
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