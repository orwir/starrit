package orwir.starrit.content.feed

import orwir.starrit.content.post.PostPreferences
import orwir.starrit.core.extension.InjectedShareable
import orwir.starrit.core.extension.Shareable
import orwir.starrit.core.extension.objPref
import orwir.starrit.core.extension.pref

class FeedPreferences : PostPreferences, Shareable by InjectedShareable() {

    var latestType: Feed.Type by objPref(Feed.Type.Home, "feed.latest_type")
    var latestSort: Feed.Sort by objPref(Feed.Sort.Best, "feed.latest_sort")
    override var blurNsfw: Boolean by pref(true, "feed.blur_nsfw")

}