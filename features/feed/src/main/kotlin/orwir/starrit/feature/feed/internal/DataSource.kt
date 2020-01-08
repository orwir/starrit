package orwir.starrit.feature.feed.internal

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
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
    private val scope: CoroutineScope
) : DataSource.Factory<String, Post>() {
    override fun create(): DataSource<String, Post> = FeedDataSource(type, sort, scope)
}

internal class FeedDataSource(
    private val type: Feed.Type,
    private val sort: Feed.Sort,
    private val scope: CoroutineScope
) : PageKeyedDataSource<String, Post>(), KoinComponent {

    private val repository: ListingRepository by inject()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Post>
    ) {
        scope.launch {
            repository
                .feed(type, sort, limit = params.requestedLoadSize)
                .apply { callback.onResult(posts, before, after) }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        scope.launch {
            repository
                .feed(type, sort, after = params.key, limit = params.requestedLoadSize)
                .apply { callback.onResult(posts, after) }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        scope.launch {
            repository
                .feed(type, sort, before = params.key, limit = params.requestedLoadSize)
                .apply { callback.onResult(posts, before) }
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

}