package orwir.starrit.listing.internal

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import orwir.starrit.access.AccessRepository
import orwir.starrit.core.model.ActionHolder
import orwir.starrit.core.model.NetworkState
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.Post
import orwir.starrit.listing.feed.PostResolver
import timber.log.Timber

internal val pageConfig = PagedList.Config.Builder()
    .setPageSize(25)
    .setInitialLoadSizeHint(50)
    .build()

internal class FeedDataSourceFactory(
    private val type: Feed.Type,
    private val sort: Feed.Sort,
    private val scope: CoroutineScope,
    private val networkState: MutableLiveData<NetworkState>,
    private val retry: ActionHolder,
    private val listingService: ListingService,
    private val userAccess: AccessRepository,
    private val postResolver: PostResolver
) : DataSource.Factory<String, Post>() {

    override fun create(): DataSource<String, Post> =
        FeedDataSource(type, sort, scope, networkState, retry, listingService, userAccess, postResolver)

}

internal class FeedDataSource(
    private val type: Feed.Type,
    private val sort: Feed.Sort,
    private val scope: CoroutineScope,
    private val networkState: MutableLiveData<NetworkState>,
    private val retry: ActionHolder,
    private val listingService: ListingService,
    private val userAccess: AccessRepository,
    private val postResolver: PostResolver
) : PageKeyedDataSource<String, Post>() {

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Post>) {
        load(limit = params.requestedLoadSize) { posts, before, after ->
            callback.onResult(posts, before, after)
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        load(after = params.key, limit = params.requestedLoadSize) { posts, _, after ->
            callback.onResult(posts, after)
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        load(before = params.key, limit = params.requestedLoadSize) { posts, before, _ ->
            callback.onResult(posts, before)
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

    private fun load(
        before: String? = null,
        after: String? = null,
        limit: Int? = null,
        callback: (List<Post>, String?, String?) -> Unit
    ) {
        scope.launch {
            try {
                networkState.postValue(NetworkState.Loading)
                listingService.listing(
                    base = userAccess.state().resolveBaseUrl(),
                    subreddit = type.asParameter(),
                    sort = sort.asParameter(),
                    before = before,
                    after = after,
                    limit = limit
                ).apply {
                    retry.action = null
                    networkState.postValue(NetworkState.Success)
                    callback(
                        data.children.map { postResolver.resolve(it.data) },
                        data.before,
                        data.after
                    )
                }
            } catch (e: Exception) {
                retry.action = { load(before, after, limit, callback) }
                networkState.postValue(NetworkState.Failure(e))
                Timber.e(e)
            }
        }
    }

}