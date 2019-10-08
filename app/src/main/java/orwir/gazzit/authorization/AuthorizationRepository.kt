package orwir.gazzit.authorization

import android.net.Uri
import androidx.lifecycle.LiveData
import java.util.*

private const val CLIENT_ID = "we9xZjW_b19qKQ"
private const val REDIRECT_URI = "gazzit://oauth"

interface AuthorizationRepository {

    fun build(): LiveData<Uri>

    fun verify(uri: Uri): LiveData<String>

    fun refresh(): LiveData<String>

    val code: String

}

class BaseAuthorizationRepository : AuthorizationRepository {

    override var code: String = ""
        private set
    private var requestState = ""
    private val scope = listOf(Scope.identity).joinToString { it.name }

    override fun build(): LiveData<Uri> {
        requestState = UUID.randomUUID().toString()
        val uri = Uri.Builder()
            .apply {
                scheme("https")
                authority("www.reddit.com")
                path("api/v1/authorize")
                appendQueryParameter("client_id", CLIENT_ID)
                appendQueryParameter("response_type", "code")
                appendQueryParameter("state", requestState)
                appendQueryParameter("redirect_uri", REDIRECT_URI)
                appendQueryParameter("duration", Duration.permanent.name)
                appendQueryParameter("scope", scope)
            }
            .build()
        return object : LiveData<Uri>(uri) {}
    }

    override fun verify(uri: Uri): LiveData<String> {
        if (uri.getQueryParameter("state") == requestState) {

        }
        TODO("not implemented")
    }

    override fun refresh(): LiveData<String> {
        TODO("not implemented")
    }
}