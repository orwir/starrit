package orwir.starrit.content.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SecureMedia(
    @Json(name = "reddit_video") val reddit: RedditVideo?,
    @Json(name = "oembed") val oembed: Oembed?
)