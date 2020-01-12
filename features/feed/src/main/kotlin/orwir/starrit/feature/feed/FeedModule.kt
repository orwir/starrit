package orwir.starrit.feature.feed

import android.view.LayoutInflater
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import orwir.starrit.feature.feed.internal.content.PostContentBinder
import orwir.starrit.listing.feed.Feed

val featureFeedModule = module {

    viewModel { (type: Feed.Type, sort: Feed.Sort) -> FeedViewModel(type, sort, get()) }

    scope(named<FeedFragment>()) {
        scoped {
            val fragment = get<FeedFragment>()
            PostContentBinder(
                fragment.viewLifecycleOwnerLiveData,
                fragment.navigation,
                LayoutInflater.from(fragment.context),
                get(),
                get()
            )
        }
    }

}