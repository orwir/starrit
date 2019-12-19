package orwir.gazzit.feed

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.gazzit.feed.model.FeedType
import orwir.gazzit.feed.model.Post
import timber.log.Timber

internal val pageConfig = PagedList.Config.Builder()
    .setPageSize(FEED_ITEMS_LIMIT)
    .setInitialLoadSizeHint(FEED_ITEMS_LIMIT * 2)
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

    private val service: FeedService by inject()
    private val resolver: PostResolver by inject()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Post>
    ) {
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
        limit: Int = FEED_ITEMS_LIMIT,
        block: (posts: List<Post>, before: String?, after: String?) -> Unit
    ) {
        scope.launch {
            try {
                val response = when (type) {
                    is FeedType.Subreddit -> service.subreddit(
                        subreddit = type.name,
                        before = before,
                        after = after,
                        limit = limit
                    )
                    is FeedType.Best -> service.best(
                        before = before,
                        after = after,
                        limit = limit
                    )
                }
                val posts = response.data.children.map { resolver.resolve(it.data) }
                block(posts, response.data.before, response.data.after)
            } catch (e: Exception) {
                Timber.e(e)
                // todo: handle exception
            }
        }
    }

}