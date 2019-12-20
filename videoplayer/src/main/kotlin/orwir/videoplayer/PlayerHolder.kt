package orwir.videoplayer

import java.lang.ref.WeakReference

internal object PlayerHolder {

    private var holder: WeakReference<PlayerView?>? = null

    fun swap(view: PlayerView) {
        holder?.get()?.release()
        holder = WeakReference(view)
    }

    fun releaseSelf(view: PlayerView) {
        if (view == holder?.get()) {
            view.release()
            holder = null
        }
    }

}