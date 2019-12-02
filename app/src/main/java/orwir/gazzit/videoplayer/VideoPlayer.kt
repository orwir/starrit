package orwir.gazzit.videoplayer

import android.app.Application
import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.GazzitApplication

val videoPlayerModule = module {

//    single { ExoPlayerFactory.newSimpleInstance(get<Application>()) as ExoPlayer }
//
//    single {
//        val agent = Util.getUserAgent(get<Context>(), GazzitApplication::class.java.simpleName)
//        DefaultDataSourceFactory(get<Application>(), agent) as DataSource.Factory
//    }

    viewModel { VideoPlayerViewModel() }

}