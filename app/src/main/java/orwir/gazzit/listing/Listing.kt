package orwir.gazzit.listing

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.listing.source.ListingService
import orwir.gazzit.common.service

val listingModule = module {

    single { service(get(), ListingService::class.java) }

    single { ListingRepository() }

    viewModel { ListingViewModel(get()) }

}