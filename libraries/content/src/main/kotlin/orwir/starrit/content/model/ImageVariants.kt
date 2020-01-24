package orwir.starrit.content.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageVariants(
    @Json(name = "obfuscated") val obfuscated: Images?,
    @Json(name = "nsfw") val nsfw: Images?
)