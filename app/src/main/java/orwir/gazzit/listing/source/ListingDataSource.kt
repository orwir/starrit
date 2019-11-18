package orwir.gazzit.listing.source

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.gazzit.model.ListingType
import orwir.gazzit.model.Post

val pageConfig = PagedList.Config.Builder()
    .setPageSize(15)
    .setInitialLoadSizeHint(15 * 3)
    .build()

class ListingDataSourceFactory(
    private val listing: ListingType,
    private val scope: CoroutineScope
) : DataSource.Factory<String, Post>() {
    override fun create(): DataSource<String, Post> = ListingDataSource(listing, scope)
}

class ListingDataSource(
    private val listing: ListingType,
    private val scope: CoroutineScope
) : PageKeyedDataSource<String, Post>(), KoinComponent {

    private val service: ListingService by inject()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Post>
    ) {
        scope.launch {
            try {
                val listing = when (listing) {
                    is ListingType.Subreddit -> service.subreddit(
                        subreddit = listing.subreddit,
                        limit = params.requestedLoadSize
                    )
                    is ListingType.Best -> service.best(limit = params.requestedLoadSize)
                }
                val posts = listing.data.children.map { it.data }
                callback.onResult(posts, listing.data.before, listing.data.after)
            } catch (ex: Exception) {
                ex.printStackTrace()
                // todo: handle exception
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        scope.launch {
            try {
                val listing = when (listing) {
                    is ListingType.Subreddit -> service.subreddit(
                        subreddit = listing.subreddit,
                        after = params.key,
                        limit = params.requestedLoadSize
                    )
                    is ListingType.Best -> service.best(
                        after = params.key,
                        limit = params.requestedLoadSize
                    )
                }
                val posts = listing.data.children.map { it.data }
                callback.onResult(posts, listing.data.after)
            } catch (ex: Exception) {
                ex.printStackTrace()
                // todo: handle exception
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        scope.launch {
            try {
                val listing = when (listing) {
                    is ListingType.Subreddit -> service.subreddit(
                        subreddit = listing.subreddit,
                        before = params.key,
                        limit = params.requestedLoadSize
                    )
                    is ListingType.Best -> service.best(
                        before = params.key,
                        limit = params.requestedLoadSize
                    )
                }
                val posts = listing.data.children.map { it.data }
                callback.onResult(posts, listing.data.before)
            } catch (ex: Exception) {
                ex.printStackTrace()
                // todo: handle exception
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}