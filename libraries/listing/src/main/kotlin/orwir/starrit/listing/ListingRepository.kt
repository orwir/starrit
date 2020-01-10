package orwir.starrit.listing

import androidx.annotation.MainThread
import kotlinx.coroutines.CoroutineScope
import orwir.starrit.listing.feed.Feed

@MainThread
interface ListingRepository {

    fun feed(type: Feed.Type, sort: Feed.Sort, scope: CoroutineScope): Feed

}