package orwir.gazzit.listing

import org.koin.dsl.module
import orwir.gazzit.core.service
import orwir.gazzit.listing.feed.PostResolver
import orwir.gazzit.listing.internal.BaseListingRepository
import orwir.gazzit.listing.internal.ListingService

val libListingModule = module {

    single<ListingService> {
        service(get(), ListingService::class.java)
    }

    single<ListingRepository> {
        BaseListingRepository()
    }

    single<PostResolver> {
        PostResolver(get())
    }

}