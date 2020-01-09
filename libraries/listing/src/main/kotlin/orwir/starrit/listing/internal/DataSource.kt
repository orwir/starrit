package orwir.starrit.listing.internal

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import orwir.starrit.authorization.AccessRepository
import orwir.starrit.core.di.Injectable
import orwir.starrit.core.di.inject
import orwir.starrit.core.model.ActionHolder
import orwir.starrit.core.model.NetworkState
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.Post
import orwir.starrit.listing.feed.PostResolver

internal val pageConfig = PagedList.Config.Builder()
    .setPageSize(25)
    .setInitialLoadSizeHint(50)
    .build()

internal class FeedDataSourceFactory(
    private val type: Feed.Type,
    private val sort: Feed.Sort,
    private val scope: CoroutineScope,
    private val networkState: MutableLiveData<NetworkState>,
    private val retry: ActionHolder
) : DataSource.Factory<String, Post>() {
    override fun create(): DataSource<String, Post> = FeedDataSource(type, sort, scope, networkState, retry)
}

internal class FeedDataSource(
    private val type: Feed.Type,
    private val sort: Feed.Sort,
    private val scope: CoroutineScope,
    private val networkState: MutableLiveData<NetworkState>,
    private val retry: ActionHolder
) : PageKeyedDataSource<String, Post>(), Injectable {

    private val service: ListingService by inject()
    private val accessRepository: AccessRepository by inject()
    private val resolver: PostResolver by inject()

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
                service.listing(
                    base = accessRepository.accessType().resolveBaseUrl(),
                    subreddit = type.asParameter(),
                    sort = sort.asParameter(),
                    before = before,
                    after = after,
                    limit = limit
                ).apply {
                    retry.action = null
                    networkState.postValue(NetworkState.Success)
                    callback(
                        data.children.map { resolver.resolve(it.data) },
                        data.before,
                        data.after
                    )
                }
            } catch (e: Exception) {
                retry.action = { load(before, after, limit, callback) }
                networkState.postValue(NetworkState.Failure(e))
            }
        }
    }

}