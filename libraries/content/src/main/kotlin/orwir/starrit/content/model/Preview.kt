package orwir.starrit.content.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Preview(
    @Json(name = "images") val images: List<Images>,
    @Json(name = "enabled") val enabled: Boolean
)