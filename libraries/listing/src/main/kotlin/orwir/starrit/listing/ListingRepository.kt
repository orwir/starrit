package orwir.starrit.listing

import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.FeedType

interface ListingRepository {

    suspend fun feed(
        type: FeedType,
        before: String? = null,
        after: String? = null,
        limit: Int? = null
    ): Feed

}