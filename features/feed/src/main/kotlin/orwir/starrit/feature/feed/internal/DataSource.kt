package orwir.starrit.feature.feed.internal

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.core.model.NetworkState
import orwir.starrit.listing.ListingRepository
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.Post

internal val pageConfig = PagedList.Config.Builder()
    .setPageSize(25)
    .setInitialLoadSizeHint(50)
    .build()

internal class FeedDataSourceFactory(
    private val type: Feed.Type,
    private val sort: Feed.Sort,
    private val scope: CoroutineScope,
    private val networkState: MutableLiveData<NetworkState>
) : DataSource.Factory<String, Post>() {
    override fun create(): DataSource<String, Post> = FeedDataSource(type, sort, scope, networkState)
}

internal class FeedDataSource(
    private val type: Feed.Type,
    private val sort: Feed.Sort,
    private val scope: CoroutineScope,
    private val networkState: MutableLiveData<NetworkState>
) : PageKeyedDataSource<String, Post>(), KoinComponent {

    private val repository: ListingRepository by inject()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Post>) {
        load(limit = params.requestedLoadSize) { callback.onResult(posts, before, after) }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        load(after = params.key, limit = params.requestedLoadSize) { callback.onResult(posts, after) }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        load(before = params.key, limit = params.requestedLoadSize) { callback.onResult(posts, before) }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

    private fun load(before: String? = null, after: String? = null, limit: Int? = null, callback: Feed.() -> Unit) {
        scope.launch {
            try {
                networkState.postValue(NetworkState.Loading)
                repository.feed(type, sort, before, after, limit).apply(callback)
                networkState.postValue(NetworkState.Success)
            } catch (e: Exception) {
                networkState.postValue(NetworkState.Failure(e))
            }
        }
    }

}