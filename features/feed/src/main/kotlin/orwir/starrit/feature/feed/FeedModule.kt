package orwir.starrit.feature.feed

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import orwir.starrit.feature.feed.internal.PostContentInflater
import orwir.starrit.listing.feed.Feed

val featureFeedModule = module {

    viewModel { (type: Feed.Type, sort: Feed.Sort) -> FeedViewModel(type, sort, get()) }

    scope(named<FeedFragment>()) {
        scoped { (navigation: FeedNavigation) -> PostContentInflater(navigation, get(), get()) }
    }

}