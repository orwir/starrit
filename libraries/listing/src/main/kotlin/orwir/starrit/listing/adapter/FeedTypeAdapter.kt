package orwir.starrit.listing.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import orwir.starrit.listing.feed.ALL
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.HOME
import orwir.starrit.listing.feed.POPULAR

class FeedTypeAdapter {
    @ToJson
    fun to(type: Feed.Type): String =
        if (type is Feed.Type.Subreddit) type.toString() else type.subreddit

    @FromJson
    fun from(raw: String): Feed.Type =
        when (raw) {
            HOME -> Feed.Type.Home
            POPULAR -> Feed.Type.Popular
            ALL -> Feed.Type.All
            else -> Feed.Type.Subreddit(raw)
        }
}