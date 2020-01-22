package orwir.starrit.listing.internal

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import kotlinx.coroutines.CoroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.access.AccessRepository
import orwir.starrit.core.model.ActionHolder
import orwir.starrit.core.model.NetworkState
import orwir.starrit.listing.ListingRepository
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.Post
import orwir.starrit.listing.feed.PostResolver
import orwir.starrit.listing.model.Suggestion

internal class BaseListingRepository : ListingRepository, KoinComponent {

    private val listingService: ListingService by inject()
    private val userAccess: AccessRepository by inject()
    private val postResolver: PostResolver by inject()

    override fun feed(type: Feed.Type, sort: Feed.Sort, scope: CoroutineScope): Feed {
        val networkState = MutableLiveData<NetworkState>()
        val retry = ActionHolder()
        val sourceFactory = FeedDataSourceFactory(
            type = type,
            sort = sort,
            scope = scope,
            networkState = networkState,
            retry = retry,
            listingService = listingService,
            userAccess = userAccess,
            postResolver = postResolver
        )
        return Feed(
            posts = LivePagedListBuilder<String, Post>(sourceFactory, pageConfig).build(),
            networkState = networkState,
            retry = retry
        )
    }

    override suspend fun suggest(query: String): Suggestion =
        listingService.suggest(userAccess.state().resolveBaseUrl(), query)
}