package orwir.gazzit.listing

import android.util.Log
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.gazzit.listing.source.ListingService

class ListingRepository : KoinComponent {

    private val service: ListingService by inject()

    suspend fun best() {
        val data = service.best()
        Log.d("!!!!!!!!", data.toString())
    }

}