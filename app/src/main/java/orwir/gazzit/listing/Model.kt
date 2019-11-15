package orwir.gazzit.listing

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
    val id: String,
    val subreddit: String,
    val title: String,
    val score: Int,
    @Json(name = "num_comments") val comments: Int,
    val author: String,
    @Json(name = "created_utc") val created: Long,
    val domain: String,
    @Json(name = "over_18") val over18: Boolean

)