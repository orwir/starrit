package orwir.starrit.content.internal.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import orwir.starrit.content.feed.ALL
import orwir.starrit.content.feed.Feed
import orwir.starrit.content.feed.HOME
import orwir.starrit.content.feed.POPULAR

class FeedTypeAdapter {
    @ToJson
    fun to(type: Feed.Type): String =
        if (type is Feed.Type.Subreddit) type.toString() else type.path

    @FromJson
    fun from(raw: String): Feed.Type =
        when (raw) {
            HOME -> Feed.Type.Home
            POPULAR -> Feed.Type.Popular
            ALL -> Feed.Type.All
            else -> Feed.Type.Subreddit(raw)
        }
}