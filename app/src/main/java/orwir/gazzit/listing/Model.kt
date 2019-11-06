package orwir.gazzit.listing

import com.squareup.moshi.Json

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
    @Json(name = "created_utc") val created: Long
)