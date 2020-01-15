package orwir.starrit.feature.feed

import orwir.starrit.core.extension.InjectedShareable
import orwir.starrit.core.extension.Shareable
import orwir.starrit.core.extension.objPref
import orwir.starrit.listing.feed.Feed

class FeedPreferences : Shareable by InjectedShareable() {

    var latestType: Feed.Type by objPref(Feed.Type.Home, "feed.latest_type")
    var latestSort: Feed.Sort by objPref(Feed.Sort.Best, "feed.latest_sort")

}