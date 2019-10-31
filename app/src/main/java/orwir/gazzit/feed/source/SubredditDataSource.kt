package orwir.gazzit.feed.source

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.gazzit.feed.model.Post

class SubredditDataSourceFactory(
    private val subreddit: String
) : DataSource.Factory<String, Post>() {
    override fun create(): DataSource<String, Post> = SubredditDataSource(subreddit)
}

class SubredditDataSource(
    private val subreddit: String
) : PageKeyedDataSource<String, Post>(), KoinComponent {

    private val service: FeedService by inject()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Post>
    ) = runBlocking {
        try {
            val listing = service.hot(subreddit = subreddit, limit = params.requestedLoadSize)
            val posts = listing.data.children.map { it.data }
            callback.onResult(posts, listing.data.before, listing.data.after)
        } catch (ex: Exception) {
            ex.printStackTrace()
            // todo: handle exception
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val listing = service.hot(
                    subreddit = subreddit,
                    after = params.key,
                    limit = params.requestedLoadSize
                )
                val posts = listing.data.children.map { it.data }
                callback.onResult(posts, listing.data.after)
            } catch (ex: Exception) {
                ex.printStackTrace()
                // todo: handle exception
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val listing = service.hot(
                    subreddit = subreddit,
                    before = params.key,
                    limit = params.requestedLoadSize
                )
                val posts = listing.data.children.map { it.data }
                callback.onResult(posts, listing.data.before)
            } catch (ex: Exception) {
                ex.printStackTrace()
                // todo: handle exception
            }
        }
    }
}