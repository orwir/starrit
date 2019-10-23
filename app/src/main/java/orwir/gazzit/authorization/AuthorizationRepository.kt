package orwir.gazzit.authorization

import android.net.Uri
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import orwir.gazzit.authorization.model.Duration
import orwir.gazzit.authorization.model.Scope
import orwir.gazzit.authorization.model.Token
import java.util.*

sealed class Step {
    data class Init(val uri: Uri) : Step()
    data class Failure(val exception: Exception) : Step()
    object Success : Step()
}

@ExperimentalCoroutinesApi
class AuthorizationRepository(private val authorizationService: Lazy<AuthorizationService>) {

    private var token: Token? = null
    private var callback: Callback? = null
    private val scope = listOf(Scope.Identity).joinToString { it.asParameter() }

    fun validToken(): Token {
        require(token != null)
        return token!!
    }

    fun startAuthRequest(): Flow<Step> = channelFlow {
        val state = UUID.randomUUID().toString()
        val uri = Uri.Builder()
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
        callback = object : Callback {

            override val state: String = state

            override fun onSuccess() {
                offer(Step.Success)
                channel.close()
            }

            override fun onError(exception: Exception) {
                offer(Step.Failure(exception))
                channel.close()
            }
        }

        offer(Step.Init(uri))

        awaitClose {
            callback = null
        }
    }

    suspend fun completeAuthRequest(uri: Uri) {
        if (uri.authority == AUTHORITY) {
            val state = uri.getQueryParameter("state")
            if (state == callback?.state) {
                val code = uri.getQueryParameter("code")!!
                try {
                    token = authorizationService.value.accessToken(code)
                    callback?.onSuccess()
                } catch (e: Exception) {
                    callback?.onError(e)
                }
            }
        }
    }

}

private interface Callback {

    val state: String

    fun onSuccess()

    fun onError(exception: Exception)

}