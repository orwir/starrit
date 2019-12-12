package orwir.gazzit.model

import android.net.Uri
import com.squareup.moshi.Json
import java.util.*

enum class Duration {
    Temporary,
    Permanent;

    fun asParameter() = name.toLowerCase(Locale.ENGLISH)
}

enum class Scope {
    Identity,
    Edit,
    Flair,
    History,
    Modconfig,
    Modflair,
    Modlog,
    Modposts,
    Modwiki,
    Mysubreddits,
    Privatemessages,
    Read,
    Report,
    Save,
    Submit,
    Subscribe,
    Vote,
    Wikiedit,
    Wikiread;

    fun asParameter() = name.toLowerCase(Locale.ENGLISH)
}

sealed class Step {
    object Idle : Step()
    data class Start(val uri: Uri) : Step()
    data class Failure(val exception: Exception) : Step()
    object Success : Step()
}

data class Token(
    @Json(name = "access_token") val access: String,
    @Json(name = "token_type") val type: String,
    @Json(name = "expires_in") val expires: Long,
    @Json(name = "scope") val scope: String,
    @Json(name = "refresh_token") val refresh: String = "",
    val obtained: Long = System.currentTimeMillis() / 1000
)