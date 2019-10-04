package orwir.gazzit

import android.content.Intent
import android.net.Uri
import java.util.*





fun authorize(state: String, openOAuth: (intent: Intent) -> Unit) {
    authorize(
        clientId = "we9xZjW_b19qKQ",
        state = state,
        redirectUri = "gazzit://oauth",
        duration = Duration.permanent,
        scope = listOf(Scope.identity),
        openOAuth = openOAuth
    )
}

fun authorize(
    clientId: String,
    state: String,
    redirectUri: String,
    duration: Duration,
    scope: List<Scope>,
    openOAuth: (intent: Intent) -> Unit
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
        .let { openOAuth(Intent(Intent.ACTION_VIEW, it)) }
}