package orwir.gazzit.model.listing

import com.squareup.moshi.Json

data class RedditVideo(
    @Json(name = "hls_url") val hls: String?
)