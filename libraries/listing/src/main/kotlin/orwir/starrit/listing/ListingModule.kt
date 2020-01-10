package orwir.starrit.listing

import android.app.Application
import org.koin.dsl.module
import orwir.starrit.core.extension.service
import orwir.starrit.listing.feed.PostResolver
import orwir.starrit.listing.internal.BaseListingRepository
import orwir.starrit.listing.internal.ListingService

val libraryListingModule = module {

    single<ListingRepository> { BaseListingRepository() }

    single { service(get(), ListingService::class.java) }

    single { PostResolver(get<Application>().resources) }

}