package orwir.gazzit.listing.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response<T>(
    val kind: Kind,
    val data: T
)