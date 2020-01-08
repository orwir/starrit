package orwir.starrit.listing

import orwir.starrit.listing.feed.Feed

interface ListingRepository {

    suspend fun feed(
        type: Feed.Type,
        sort: Feed.Sort = Feed.Sort.Best,
        before: String? = null,
        after: String? = null,
        limit: Int? = null
    ): Feed

}