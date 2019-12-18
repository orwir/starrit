package orwir.gazzit.model.listing

import com.squareup.moshi.Json

data class PreviewImages(
    @Json(name = "source") val source: PreviewImage,
    @Json(name = "resolutions") val resolutions: List<PreviewImage>,
    @Json(name = "variants") val variants: PreviewVariants?
)