package orwir.gazzit

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.util.*

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

fun authorize(context: Context, state: String) {
    authorize(
        context = context,
        clientId = "we9xZjW_b19qKQ",
        state = state,
        redirectUri = "gazzit://oauth",
        duration = Duration.permanent,
        scope = listOf(Scope.identity)
    )
}

fun authorize(
    context: Context,
    clientId: String,
    state: String,
    redirectUri: String,
    duration: Duration,
    scope: List<Scope>
) {
    Uri.Builder()
        .apply {
            scheme("https")
            authority("www.reddit.com")
            path("api/v1/authorize")
            appendQueryParameter("client_id", clientId)
            appendQueryParameter("response_type", "code")
            appendQueryParameter("state", state)
            appendQueryParameter("redirect_uri", redirectUri)
            appendQueryParameter("duration", duration.name.toLowerCase(Locale.ENGLISH))
            appendQueryParameter("scope", scope.joinToString { it.name.toLowerCase(Locale.ENGLISH) })
        }
        .build()
        .let { context.startActivity(Intent(Intent.ACTION_VIEW, it)) }
}