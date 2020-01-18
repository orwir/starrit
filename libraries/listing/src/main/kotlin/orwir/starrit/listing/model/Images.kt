package orwir.starrit.listing.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images(
    @Json(name = "source") val source: Image,
    @Json(name = "resolutions") val resolutions: List<Image>,
    @Json(name = "variants") val variants: ImageVariants?
)