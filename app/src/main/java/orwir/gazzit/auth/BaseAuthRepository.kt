package orwir.gazzit.auth

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import orwir.gazzit.source.Authorization
import java.util.*

class BaseAuthRepository : AuthRepository {

    override val token: LiveData<Token?> = MutableLiveData(null)
    private var state = ""
    private val scope = listOf(Scope.identity).joinToString(separator = " ") { it.name }

    override fun buildAuthUri(): Uri {
        state = UUID.randomUUID().toString()
        return Uri.Builder()
            .apply {
                scheme("https")
                authority("www.reddit.com")
                path("api/v1/authorize.compact")
                appendQueryParameter("client_id", CLIENT_ID)
                appendQueryParameter("response_type", "code")
                appendQueryParameter("state", state)
                appendQueryParameter("redirect_uri", REDIRECT_URI)
                appendQueryParameter("duration", Duration.permanent.name)
                appendQueryParameter("scope", scope)
            }
            .build()
    }

    override fun handleAuthCode(uri: Uri) {
        if (uri.authority == AUTHORITY) {
            val state = uri.getQueryParameter("state")
            require(state == this.state) { "Request state [${this.state}] and response state [${state}] don't much" }

            val code = uri.getQueryParameter("code")
            (token as MutableLiveData).postValue(Token("", "", 1, "", ""))
        }
    }
}