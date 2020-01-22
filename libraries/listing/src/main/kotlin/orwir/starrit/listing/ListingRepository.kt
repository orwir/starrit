package orwir.starrit.listing

import androidx.annotation.MainThread
import kotlinx.coroutines.CoroutineScope
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.model.Suggest

@MainThread
interface ListingRepository {

    fun feed(type: Feed.Type, sort: Feed.Sort, scope: CoroutineScope): Feed

    suspend fun suggest(startsWith: String): Suggest

}