package orwir.starrit.listing.internal

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import kotlinx.coroutines.CoroutineScope
import orwir.starrit.core.model.ActionHolder
import orwir.starrit.core.model.NetworkState
import orwir.starrit.listing.ListingRepository
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.Post

internal class BaseListingRepository : ListingRepository {

    override fun feed(type: Feed.Type, sort: Feed.Sort, scope: CoroutineScope): Feed {
        val networkState = MutableLiveData<NetworkState>()
        val retry = ActionHolder()
        val sourceFactory = FeedDataSourceFactory(type, sort, scope, networkState, retry)

        return Feed(
            posts = LivePagedListBuilder<String, Post>(sourceFactory, pageConfig).build(),
            networkState = networkState,
            retry = retry
        )
    }

}