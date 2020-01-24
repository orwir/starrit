package orwir.starrit.content.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Subreddit(
    @Json(name = "display_name") val name: String,
    @Json(name = "community_icon") internal val communityIcon: String?,
    @Json(name = "icon_img") internal val iconImg: String?,
    @Json(name = "banner_img") internal val bannerImg: String?,
    @Json(name = "header_img") internal val headerImg: String?
) : Serializable {
    val icon: String =
        communityIcon?.takeIf { it.isNotBlank() }
            ?: iconImg?.takeIf { it.isNotBlank() }
            ?: ""

    val banner: String =
        bannerImg?.takeIf { it.isNotBlank() }
            ?: headerImg?.takeIf { it.isNotBlank() }
            ?: icon
}