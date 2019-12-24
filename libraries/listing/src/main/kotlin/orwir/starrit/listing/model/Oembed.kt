package orwir.starrit.listing.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Oembed(
    @Json(name = "thumbnail_url") val thumbnail: String?
)