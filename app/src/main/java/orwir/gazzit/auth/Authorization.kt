package orwir.gazzit.auth

import android.net.Uri
import androidx.lifecycle.LiveData
import com.squareup.moshi.Json
import org.koin.dsl.module
import java.util.*

val authorizationModule = module {
    single { AuthInterceptor(get()) }
    single<AuthRepository> { BaseAuthRepository(get()) }
}

internal const val CLIENT_ID = "we9xZjW_b19qKQ"
internal const val CLIENT_ID64 = "d2U5eFpqV19iMTlxS1E6"
internal const val AUTHORITY = "oauth"
internal const val REDIRECT_URI = "gazzit://${AUTHORITY}"

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

data class Token(
    @Json(name = "access_token") val access: String,
    @Json(name = "token_type") val type: String,
    @Json(name = "expires_in") val expires: Int,
    @Json(name = "scope") val scope: String,
    @Json(name = "refresh_token") val refresh: String
)

interface AuthRepository {

    val token: LiveData<Token?>

    fun buildAuthUri(): Uri

    fun handleAuthCode(uri: Uri)

}