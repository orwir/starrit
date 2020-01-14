package orwir.videoplayer

import androidx.annotation.MainThread
import java.lang.ref.WeakReference

internal object VideoPlayerHolder {

    private var holder: WeakReference<VideoPlayer?>? = null

    @MainThread
    fun swap(view: VideoPlayer) {
        holder?.get()?.release()
        holder = WeakReference(view)
    }

    @MainThread
    fun releaseSelf(view: VideoPlayer) {
        if (view == holder?.get()) {
            view.release()
            holder = null
        }
    }

}