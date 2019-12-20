package orwir.gazzit.model.authorization

import com.squareup.moshi.Json

data class Token(
    @Json(name = "access_token") val access: String,
    @Json(name = "token_type") val type: String,
    @Json(name = "expires_in") val expires: Long,
    @Json(name = "scope") val scope: String,
    @Json(name = "refresh_token") val refresh: String = "",
    val obtained: Long = System.currentTimeMillis() / 1000
)