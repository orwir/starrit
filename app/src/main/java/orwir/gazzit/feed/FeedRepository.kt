package orwir.gazzit.feed

import android.util.Log
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.gazzit.feed.source.FeedService

class FeedRepository : KoinComponent {

    private val service: FeedService by inject()

    suspend fun best() {
        val data = service.best()
        Log.d("!!!!!!!!", data.toString())
    }

}