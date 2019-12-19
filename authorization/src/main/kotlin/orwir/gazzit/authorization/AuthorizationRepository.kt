package orwir.gazzit.authorization

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import orwir.gazzit.model.authorization.Duration
import orwir.gazzit.model.authorization.Step
import orwir.gazzit.model.authorization.Token

interface AuthorizationRepository {
    suspend fun obtainToken(): Token
    fun authorizationFlow(): Flow<Step>
    fun authorizationFlowStart()
    fun authorizationFlowComplete(response: Uri)
}

internal fun buildAuthorizationUri(state: String, scope: String) =
    Uri.Builder()
        .apply {
            scheme("https")
            authority("www.reddit.com")
            path("api/v1/authorize.compact")
            appendQueryParameter("client_id", BuildConfig.CLIENT_ID)
            appendQueryParameter("response_type", "code")
            appendQueryParameter("state", state)
            appendQueryParameter("redirect_uri", "${BuildConfig.SCHEMA}://${BuildConfig.HOST}")
            appendQueryParameter("duration", Duration.Permanent.asParameter())
            appendQueryParameter("scope", scope)
        }
        .build()