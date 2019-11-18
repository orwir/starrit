package orwir.gazzit.listing

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.common.service
import orwir.gazzit.listing.source.ListingService
import orwir.gazzit.model.ListingType

val listingModule = module {

    single { service(get(), ListingService::class.java) }

    viewModel { (listing: ListingType) -> ListingViewModel(listing) }

}