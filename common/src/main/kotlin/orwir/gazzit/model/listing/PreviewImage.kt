package orwir.gazzit.model.listing

import com.squareup.moshi.Json

data class PreviewImage(
    @Json(name = "url") val url: String,
    @Json(name = "width") val width: Int,
    @Json(name = "height") val height: Int
)