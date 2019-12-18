package orwir.gazzit.feed.model

import java.io.Serializable

sealed class FeedType : Serializable {
    object Best : FeedType()
    data class Subreddit(val url: String) : FeedType()
}