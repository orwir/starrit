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
import orwir.starrit.authorization.AuthorizationRepository
import orwir.starrit.authorization.BuildConfig
import orwir.starrit.authorization.TokenException
import orwir.starrit.authorization.model.Duration
import orwir.starrit.authorization.model.Scope
import orwir.starrit.authorization.model.Step
import orwir.starrit.authorization.model.Token
import orwir.starrit.core.extension.KoinedShareable
import orwir.starrit.core.extension.Shareable
import orwir.starrit.core.extension.objPref
import java.util.*

internal class BasicAuthorizationRepository :
    AuthorizationRepository,
    TokenRepository,
    KoinComponent,
    Shareable by KoinedShareable(),
    CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private var requestState: String? by objPref(null)
    private var requestCallback: Callback? = null
    private var responseUri: Uri? = null

    private var token: Token? by objPref(null)
    private val service: AuthorizationService by inject()
    private val scope = listOf(Scope.Identity, Scope.Read).joinToString { it.asParameter() }

    override suspend fun isAuthorized() = try {
        obtainToken(); true
    } catch (e: Exception) {
        false
    }

    override suspend fun obtainToken(): Token {
        return token?.let {
            if (it.isExpired()) {
                try {
                    token = service.refreshToken(it.refresh).copy(refresh = it.refresh)
                } catch (e: Exception) {
                    throw TokenException("Token refresh failed!", e)
                }
            }
            token
        } ?: throw TokenException("Token not found!")
    }

    @ExperimentalCoroutinesApi
    override fun authorizationFlow(): Flow<Step> = callbackFlow {
        if (requestCallback == null) {
            requestCallback = object : Callback {

                override fun onReset() {
                    requestState = null
                    offer(Step.Idle)
                }

                override fun onStart() {
                    requestState = requestState ?: UUID.randomUUID().toString()
                    responseUri = null
                    offer(Step.Start(buildAuthorizationUri(requestState!!, scope)))
                }

                override fun onSuccess() {
                    offer(Step.Success)
                    requestState = null
                    responseUri = null
                    channel.close()
                }

                override fun onError(exception: Exception) {
                    offer(Step.Failure(exception))
                    requestState = null
                    responseUri = null
                }

            }
        }

        if (responseUri == null) {
            offer(Step.Idle)
        } else {
            authorizationFlowComplete(responseUri!!)
        }

        awaitClose {
            requestCallback = null
        }
    }

    override fun authorizationFlowStart() {
        requestCallback?.onStart()
    }

    override fun authorizationFlowComplete(response: Uri) {
        if (requestCallback != null) {
            val callback = requestCallback!!
            launch {
                val state = response.getQueryParameter("state")
                if (state == requestState) {
                    val error = response.getQueryParameter("error")
                        ?.toUpperCase(Locale.ENGLISH)
                        ?.let(TokenException.ErrorCode::valueOf)
                    if (error == null) {
                        val code = response.getQueryParameter("code")!!
                        try {
                            token = service.accessToken(code)
                            callback.onSuccess()
                        } catch (e: Exception) {
                            callback.onError(e)
                        }
                    } else {
                        callback.onError(TokenException("API Error", code = error))
                    }
                } else {
                    callback.onError(TokenException("Request/Response state mismatch: e:[$requestState], a:[$state]"))
                }
            }
        } else {
            responseUri = response
        }
    }

    override fun authorizationFlowReset() {
        requestCallback?.onReset()
    }
}

private interface Callback {
    fun onReset()
    fun onStart()
    fun onSuccess()
    fun onError(exception: Exception)
}

private const val ADDITIONAL_THRESHOLD_S = 5

private fun Token.isExpired() =
    System.currentTimeMillis() / 1000 >= (obtained + expires - ADDITIONAL_THRESHOLD_S)

private fun buildAuthorizationUri(state: String, scope: String) =
    Uri.Builder()
        .apply {
            scheme("https")
            authority("www.reddit.com")
            path("api/v1/authorize.compact")
            appendQueryParameter("client_id", BuildConfig.CLIENT_ID)
            appendQueryParameter("response_type", "code")
            appendQueryParameter("state", state)
            appendQueryParameter("redirect_uri", BuildConfig.REDIRECT_URI)
            appendQueryParameter("duration", Duration.Permanent.asParameter())
            appendQueryParameter("scope", scope)
        }
        .build()