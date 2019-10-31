package orwir.gazzit.authorization

import android.net.Uri
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.gazzit.BuildConfig
import orwir.gazzit.authorization.model.Duration
import orwir.gazzit.authorization.model.Scope
import orwir.gazzit.authorization.model.Step
import orwir.gazzit.authorization.model.Token
import orwir.gazzit.util.KoinedShareable
import orwir.gazzit.util.Shareable
import orwir.gazzit.util.objPref
import java.util.*

class AuthorizationRepository : KoinComponent, Shareable by KoinedShareable() {

    private var token: Token? by objPref(null)
    private var callback: Callback? = null
    private val service: AuthorizationService by inject()
    private val scope = listOf(Scope.Identity, Scope.Read).joinToString { it.asParameter() }

    fun hasToken(): Boolean = token != null

    fun obtainToken(): Token = runBlocking {
        token?.let {
            if ((it.obtained + it.expires - 5000) >= System.currentTimeMillis()) {
                token = service.refreshToken(it.refresh).copy(refresh = it.refresh)
                token
            } else {
                it
            }
        } ?: throw IllegalStateException("Token not found!")
    }

    @ExperimentalCoroutinesApi
    fun authorizationFlow(): Flow<Step> = channelFlow {
        val state = UUID.randomUUID().toString()

        callback = object : Callback {

            override val state: String = state

            override fun onStart() {
                offer(Step.Start(buildAuthorizationUri(state, scope)))
            }

            override fun onSuccess() {
                offer(Step.Success)
                channel.close()
            }

            override fun onError(exception: Exception) {
                offer(Step.Failure(exception))
                channel.close()
            }
        }

        offer(Step.Idle)

        awaitClose {
            callback = null
        }
    }

    fun startAuthorization() {
        callback?.onStart()
    }

    fun completeAuthorization(uri: Uri) {
        if (uri.authority == BuildConfig.HOST) {
            GlobalScope.launch {
                val state = uri.getQueryParameter("state")
                if (state == callback?.state) {
                    val code = uri.getQueryParameter("code")!!
                    try {
                        token = service.accessToken(code)
                        callback?.onSuccess()
                    } catch (e: Exception) {
                        callback?.onError(e)
                    }
                } else {
                    callback?.onError(IllegalStateException("Response state is not matched with the request"))
                }
            }
        }
    }
}

internal interface Callback {

    val state: String

    fun onStart()

    fun onSuccess()

    fun onError(exception: Exception)

}

internal fun buildAuthorizationUri(state: String, scope: String) =
    Uri.Builder()
        .apply {
            scheme("https")
            authority("www.reddit.com")
            path("api/v1/authorize.compact")
            appendQueryParameter("client_id", BuildConfig.GAZZIT_CLIENT_ID)
            appendQueryParameter("response_type", "code")
            appendQueryParameter("state", state)
            appendQueryParameter("redirect_uri", REDIRECT_URI)
            appendQueryParameter("duration", Duration.Permanent.asParameter())
            appendQueryParameter("scope", scope)
        }
        .build()