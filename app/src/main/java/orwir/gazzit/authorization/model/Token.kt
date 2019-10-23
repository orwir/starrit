package orwir.gazzit.authorization.model

import com.squareup.moshi.Json

data class Token(
    @Json(name = "access_token") val access: String,
    @Json(name = "token_type") val type: String,
    @Json(name = "expires_in") val expires: Int,
    @Json(name = "scope") val scope: String,
    @Json(name = "refresh_token") val refresh: String
)