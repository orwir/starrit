package orwir.gazzit.feature.feed.internal

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.gazzit.listing.ListingRepository
import orwir.gazzit.listing.feed.FeedType
import orwir.gazzit.listing.feed.Post

internal val pageConfig = PagedList.Config.Builder()
    .setPageSize(25)
    .setInitialLoadSizeHint(50)
    .build()

internal class FeedDataSourceFactory(
    private val type: FeedType,
    private val scope: CoroutineScope
) : DataSource.Factory<String, Post>() {
    override fun create(): DataSource<String, Post> = FeedDataSource(type, scope)
}

internal class FeedDataSource(
    private val type: FeedType,
    private val scope: CoroutineScope
) : PageKeyedDataSource<String, Post>(), KoinComponent {

    private val repository: ListingRepository by inject()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Post>
    ) {
        scope.launch {
            repository
                .feed(type, limit = params.requestedLoadSize)
                .apply { callback.onResult(posts, before, after) }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        scope.launch {
            repository
                .feed(type, after = params.key, limit = params.requestedLoadSize)
                .apply { callback.onResult(posts, after) }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        scope.launch {
            repository
                .feed(type, before = params.key, limit = params.requestedLoadSize)
                .apply { callback.onResult(posts, before) }
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

}