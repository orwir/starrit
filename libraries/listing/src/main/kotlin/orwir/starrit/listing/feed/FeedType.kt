package orwir.starrit.listing.feed

import java.io.Serializable

sealed class FeedType : Serializable {
    object Best : FeedType()
    data class Subreddit(val name: String) : FeedType()
}