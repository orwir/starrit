package orwir.gazzit.listing.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Subreddit(
    @Json(name = "display_name") val name: String,
    @Json(name = "community_icon") val communityIcon: String?,
    @Json(name = "icon_img") val iconImg: String?,
    @Json(name = "banner_img") val bannerImg: String?,
    @Json(name = "header_img") val headerImg: String?
) {
    val icon: String =
        communityIcon?.takeIf { it.isNotBlank() }
            ?: iconImg?.takeIf { it.isNotBlank() }
            ?: ""

    val banner: String =
        bannerImg?.takeIf { it.isNotBlank() }
            ?: headerImg?.takeIf { it.isNotBlank() }
            ?: icon
}