package orwir.gazzit.listing.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Preview(
    @Json(name = "images") val images: List<PreviewImages>,
    @Json(name = "enabled") val enabled: Boolean
)