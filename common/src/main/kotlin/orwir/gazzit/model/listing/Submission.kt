package orwir.gazzit.model.listing

import com.squareup.moshi.Json

data class Submission(
    @Json(name = "id") val id: String,
    @Json(name = "sr_detail") val subreddit: Subreddit,
    @Json(name = "author") val author: String,
    @Json(name = "created_utc") val created: Long,
    @Json(name = "title") val title: String,
    @Json(name = "over_18") val nsfw: Boolean,
    @Json(name = "spoiler") val spoiler: Boolean,
    @Json(name = "num_comments") val commentsCount: Int,
    @Json(name = "score") val score: Int,
    @Json(name = "hide_score") val hideScore: Boolean,
    @Json(name = "domain") val domain: String,
    @Json(name = "url") val url: String,
    @Json(name = "thumbnail") val thumbnail: String,
    @Json(name = "preview") val preview: Preview?,
    @Json(name = "selftext") val selftext: String?,
    @Json(name = "selftext_html") val selftextHtml: String?
)