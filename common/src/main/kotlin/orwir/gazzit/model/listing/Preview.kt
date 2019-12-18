package orwir.gazzit.model.listing

import com.squareup.moshi.Json

data class Preview(
    @Json(name = "images") val images: List<PreviewImages>,
    @Json(name = "enabled") val enabled: Boolean
)