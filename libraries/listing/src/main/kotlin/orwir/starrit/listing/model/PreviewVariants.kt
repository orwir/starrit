package orwir.starrit.listing.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PreviewVariants(
    @Json(name = "obfuscated") val obfuscated: PreviewImages?,
    @Json(name = "nsfw") val nsfw: PreviewImages?
)