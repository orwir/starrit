package orwir.gazzit.feed

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.common.service
import orwir.gazzit.feed.model.FeedType

val feedModule = module {

    single<FeedService> {
        service(get(), FeedService::class.java)
    }

    single<PostResolver> {
        PostResolver(get())
    }

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

internal const val FEED_ITEMS_LIMIT = 15