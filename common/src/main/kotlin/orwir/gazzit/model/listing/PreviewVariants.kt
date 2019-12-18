package orwir.gazzit.model.listing

import com.squareup.moshi.Json

data class PreviewVariants(
    @Json(name = "obfuscated") val obfuscated: PreviewImages?,
    @Json(name = "nsfw") val nsfw: PreviewImages?
)