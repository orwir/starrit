package orwir.starrit.feature.feed

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.starrit.feature.feed.internal.ContentInflater
import orwir.starrit.listing.feed.FeedType

internal val feedModule = module {

    single<ContentInflater> {
        ContentInflater()
    }

    single<ExoPlayer> {
        SimpleExoPlayer.Builder(get())
            .setUseLazyPreparation(true)
            .build()
    }

    viewModel { (type: FeedType) -> FeedViewModel(type) }

}