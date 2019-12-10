package orwir.gazzit.authorization

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import orwir.gazzit.model.Duration
import orwir.gazzit.model.Step
import orwir.gazzit.model.Token

interface AuthorizationRepository {
    fun hasToken(): Boolean
    fun obtainToken(): Token
}

//interface AuthorizationFlow {
//    fun flow(): Flow<Step>
//    fun start()
//    fun complete(uri: Uri)
//}
//
//internal interface AuthorizationCallback {
//    val state: String
//    fun onStart()
//    fun onSuccess()
//    fun onError(exception: Exception)
//}

internal fun buildAuthorizationUri(state: String, scope: String) =
    Uri.Builder()
        .apply {
            scheme("https")
            authority("www.reddit.com")
            path("api/v1/authorize.compact")
            appendQueryParameter("client_id", BuildConfig.GAZZIT_CLIENT_ID)
            appendQueryParameter("response_type", "code")
            appendQueryParameter("state", state)
            appendQueryParameter("redirect_uri", "${BuildConfig.SCHEMA}://${BuildConfig.HOST}")
            appendQueryParameter("duration", Duration.Permanent.asParameter())
            appendQueryParameter("scope", scope)
        }
        .build()