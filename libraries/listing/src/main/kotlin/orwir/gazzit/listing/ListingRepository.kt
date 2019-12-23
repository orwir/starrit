package orwir.gazzit.listing

import orwir.gazzit.listing.feed.Feed
import orwir.gazzit.listing.feed.FeedType

interface ListingRepository {

    suspend fun feed(
        type: FeedType,
        before: String? = null,
        after: String? = null,
        limit: Int? = null
    ): Feed

}