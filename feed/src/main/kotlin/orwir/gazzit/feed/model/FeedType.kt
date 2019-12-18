package orwir.gazzit.feed.model

sealed class FeedType {
    object Best : FeedType()
    data class Subreddit(val url: String) : FeedType()
}