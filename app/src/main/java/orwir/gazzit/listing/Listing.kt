package orwir.gazzit.listing

import android.app.Application
import android.content.Context
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.GazzitApplication
import orwir.gazzit.common.service
import orwir.gazzit.listing.source.ListingService
import orwir.gazzit.model.ListingType

val listingModule = module {

    single { service(get(), ListingService::class.java) }

    single {
        val agent = Util.getUserAgent(get<Context>(), GazzitApplication::class.java.simpleName)
        DefaultDataSourceFactory(get<Application>(), agent) as DataSource.Factory
    }

    viewModel { (listing: ListingType) -> ListingViewModel(listing) }

}