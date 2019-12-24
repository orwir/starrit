package orwir.starrit.listing

import org.koin.dsl.module
import orwir.starrit.core.service
import orwir.starrit.listing.feed.PostResolver
import orwir.starrit.listing.internal.BaseListingRepository
import orwir.starrit.listing.internal.ListingService

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