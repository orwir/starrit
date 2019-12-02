package orwir.gazzit.videoplayer

import android.app.Application
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val videoPlayerModule = module {

    factory { ExoPlayerFactory.newSimpleInstance(get<Application>()) as ExoPlayer }

    viewModel { VideoPlayerViewModel() }

}