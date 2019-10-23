package orwir.gazzit.feed

import android.util.Log
import orwir.gazzit.source.remote.FeedService
import java.util.concurrent.Executors

class BaseFeedRepository(
    private val feedService: FeedService
) : FeedRepository {

    override fun best() {
        Executors.newSingleThreadExecutor().execute {
            val response = feedService.best().execute()
            Log.d("!!!!", response.code().toString())
        }
    }
}