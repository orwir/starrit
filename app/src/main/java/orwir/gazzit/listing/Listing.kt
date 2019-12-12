package orwir.gazzit.listing

import android.app.Application
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.common.service
import orwir.gazzit.model.ListingType

val listingModule = module {

    single { service(get(), ListingService::class.java) }

    factory { ExoPlayerFactory.newSimpleInstance(get<Application>()) as ExoPlayer }

    viewModel { (listing: ListingType) -> ListingViewModel(listing) }

}