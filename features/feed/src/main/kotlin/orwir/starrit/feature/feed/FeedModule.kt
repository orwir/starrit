package orwir.starrit.feature.feed

import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import orwir.starrit.feature.feed.internal.content.PostContentBinder
import orwir.starrit.listing.feed.Feed

val featureFeedModule = module {

    viewModel { (type: Feed.Type, sort: Feed.Sort) -> FeedViewModel(type, sort, get()) }

    scope(named<FeedFragment>()) {
        scoped { (owner: LifecycleOwner, navigation: FeedNavigation, inflater: LayoutInflater) ->
            PostContentBinder(owner, navigation, inflater, get(), get())
        }
    }

}