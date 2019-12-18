package orwir.gazzit.model.listing

import com.squareup.moshi.Json

data class Subreddit(
    @Json(name = "display_name") val name: String,
    @Json(name = "icon_img") val icon: String
)