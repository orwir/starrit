package orwir.videoplayer

import androidx.annotation.MainThread
import com.google.android.exoplayer2.ExoPlayer
import java.lang.ref.WeakReference

internal object VideoPlayerHolder {

    private lateinit var player: ExoPlayer
    private var holder: WeakReference<VideoPlayer?>? = null

    @MainThread
    fun onPlayerInit(player: ExoPlayer) {
        this.player = player
        holder?.get()?.apply {
            setPlayer(player)
            start(true)
        }
    }

    @MainThread
    fun swap(view: VideoPlayer) {
        holder?.get()?.takeIf { it != view }?.release()
        holder = WeakReference(view)
        view.setPlayer(player)
    }

    @MainThread
    fun releaseSelf(view: VideoPlayer) {
        if (view == holder?.get()) {
            view.release()
            holder = null
        }
    }

    @MainThread
    fun onPlayerRelease() {
        holder?.get()?.release()
    }

}