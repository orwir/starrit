package orwir.videoplayer

import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.ExoPlayer

fun LifecycleOwner.bindVideoPlayer(playerFactory: () -> ExoPlayer) {
    lifecycle.addObserver(VideoPlayerObserver(playerFactory))
}

class VideoPlayerObserver(private val playerFactory: () -> ExoPlayer) : LifecycleObserver {

    private lateinit var player: ExoPlayer
    private var playOnResume: Boolean = false
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0

    @OnLifecycleEvent(ON_START)
    fun initializePlayer() {
        player = playerFactory.invoke()
        player.playWhenReady = playOnResume
        player.seekTo(currentWindow, playbackPosition)
        VideoPlayerHolder.onPlayerInit(player)
    }

    @OnLifecycleEvent(ON_STOP)
    fun releasePlayer() {
        playOnResume = player.playWhenReady
        currentWindow = player.currentWindowIndex
        playbackPosition = player.currentPosition
        VideoPlayerHolder.onPlayerRelease()
        player.release()
    }

}