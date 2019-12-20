package orwir.gazzit.model.listing

import com.squareup.moshi.Json

data class SecureMedia(
    @Json(name = "reddit_video") val reddit: RedditVideo?,
    @Json(name = "oembed") val oembed: Oembed?
)