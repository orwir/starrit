package orwir.gazzit.model

import com.squareup.moshi.Json

sealed class ListingType {
    companion object {
        fun parse(arg: String): ListingType = when {
            arg.startsWith("r/") -> Subreddit(arg.substring(2))
            arg == "best" -> Best
            else -> throw IllegalArgumentException("cannot recognize type of '$arg'")
        }
    }

    data class Subreddit(val subreddit: String) : ListingType()
    object Best : ListingType()
}

data class Listing(
    val kind: String,
    val data: ListingData
)

data class ListingData(
    val children: List<Child>,
    val after: String?,
    val before: String?
)

data class Child(
    val kind: String,
    val data: Post
)

data class Post(
    @Json(name = "id") val id: String,
    @Json(name = "subreddit") val subreddit: String,
    @Json(name = "title") val title: String,
    @Json(name = "score") val score: Int,
    @Json(name = "num_comments") val comments: Int,
    @Json(name = "author") val author: String,
    @Json(name = "created_utc") val created: Long,
    @Json(name = "domain") val domain: String,
    @Json(name = "over_18") val nsfw: Boolean,
    @Json(name = "spoiler") val spoiler: Boolean,
    @Json(name = "hide_score") val hideScore: Boolean,
    @Json(name = "post_hint") val hint: String?,
    @Json(name = "selftext") val text: String?,
    @Json(name = "url") val url: String,
    @Json(name = "thumbnail") val thumbnail: String,
    @Json(name = "preview") val preview: Preview?
)

data class Preview(val images: List<PostImage>, val enabled: Boolean)

data class PostImage(val source: ImagePreview, val resolutions: List<ImagePreview>)

data class ImagePreview(val url: String, val width: Int, val height: Int)