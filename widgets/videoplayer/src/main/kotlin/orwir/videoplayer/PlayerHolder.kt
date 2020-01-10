package orwir.videoplayer

import androidx.annotation.MainThread
import java.lang.ref.WeakReference

internal object PlayerHolder {

    private var holder: WeakReference<PlayerView?>? = null

    @MainThread
    fun swap(view: PlayerView) {
        holder?.get()?.release()
        holder = WeakReference(view)
    }

    @MainThread
    fun releaseSelf(view: PlayerView) {
        if (view == holder?.get()) {
            view.release()
            holder = null
        }
    }

}