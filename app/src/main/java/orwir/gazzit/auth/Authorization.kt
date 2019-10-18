package orwir.gazzit.auth

import android.net.Uri
import androidx.lifecycle.LiveData
import com.squareup.moshi.Json
import org.koin.dsl.module

val authorizationModule = module {
    single<AuthRepository> { BaseAuthRepository(get()) }
}

internal const val CLIENT_ID = "we9xZjW_b19qKQ"
internal const val AUTHORITY = "oauth"
internal const val REDIRECT_URI = "gazzit://${AUTHORITY}"

enum class Duration {
    temporary,
    permanent
}

enum class Scope {
    identity,
    edit,
    flair,
    history,
    modconfig,
    modflair,
    modlog,
    modposts,
    modwiki,
    mysubreddits,
    privatemessages,
    read,
    report,
    save,
    submit,
    subscribe,
    vote,
    wikiedit,
    wikiread
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