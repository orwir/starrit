package orwir.starrit.listing.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PreviewImages(
    @Json(name = "source") val source: PreviewImage,
    @Json(name = "resolutions") val resolutions: List<PreviewImage>,
    @Json(name = "variants") val variants: PreviewVariants?
)