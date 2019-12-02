package orwir.gazzit.videoplayer

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.gazzit.R
import orwir.gazzit.common.launchActivity
import orwir.gazzit.databinding.ActivityVideoplayerBinding

private const val EXTRA_URI = "extra_uri"

class VideoPlayerActivity : AppCompatActivity() {

    companion object {
        fun play(context: Context, uri: Uri) =
            context.launchActivity(VideoPlayerActivity::class.java) {
                putExtra(EXTRA_URI, uri)
            }
    }

    private val viewModel: VideoPlayerViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil
            .setContentView<ActivityVideoplayerBinding>(this, R.layout.activity_videoplayer)
            .also {
                it.viewModel = viewModel
                it.lifecycleOwner = this
            }
    }

    override fun onStop() {
        super.onStop()
        viewModel.player.playWhenReady = false
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}

class VideoPlayerViewModel : ViewModel(), KoinComponent {

    val player: ExoPlayer by inject()

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

}