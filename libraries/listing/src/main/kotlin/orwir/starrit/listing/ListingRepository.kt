package orwir.starrit.listing

import androidx.annotation.MainThread
import kotlinx.coroutines.CoroutineScope
import orwir.starrit.listing.feed.Feed

interface ListingRepository {

    @MainThread
    fun feed(type: Feed.Type, sort: Feed.Sort, scope: CoroutineScope): Feed

}