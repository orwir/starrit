package orwir.starrit.feature.feed

import android.view.LayoutInflater
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import orwir.starrit.feature.feed.internal.content.PostContentBinder
import orwir.starrit.listing.feed.Feed

val featureFeedModule = module {

    single { FeedPreferences() }

    viewModel { (type: Feed.Type, sort: Feed.Sort) -> FeedViewModel(type, sort, get()) }

    viewModel { (type: Feed.Type, sort: Feed.Sort) -> SelectionViewModel(type, sort) }

    scope(named<FeedFragment>()) {
        scoped {
            val fragment = get<FeedFragment>()
            PostContentBinder(
                navigation = fragment.navigation,
                inflater = LayoutInflater.from(fragment.context),
                player = get(),
                ownerLiveData = fragment.viewLifecycleOwnerLiveData
            )
        }
    }

}

internal const val FEED_TYPE = "type"
internal const val FEED_SORT = "sort"