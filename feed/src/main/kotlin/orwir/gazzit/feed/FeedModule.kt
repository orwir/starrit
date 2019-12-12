package orwir.gazzit.feed

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.common.service
import orwir.gazzit.model.ListingType

val feedModule = module {

    single<FeedService> {
        service(get(), FeedService::class.java)
    }

    viewModel { (type: ListingType, navigation: FeedNavigation) ->
        FeedViewModel(type, navigation)
    }

}

internal const val FEED_ITEMS_LIMIT = 15