package orwir.gazzit.model.listing

import com.squareup.moshi.Json

data class Oembed(
    @Json(name = "thumbnail_url") val thumbnail: String?
)