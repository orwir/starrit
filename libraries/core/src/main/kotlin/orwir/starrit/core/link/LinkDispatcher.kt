package orwir.starrit.core.link

import android.net.Uri
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.util.*

class LinkDispatcher {

    private val callbacks = ArrayDeque<LinkCallback>()

    @MainThread
    fun addCallback(owner: LifecycleOwner, builder: LinkCallbackBuilder.() -> Unit) {
        if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            return
        }
        LinkCallbackBuilder()
            .apply(builder)
            .build()
            .also {
                owner.lifecycle.addObserver(DestroyObserver(it))
                callbacks.add(it)
            }
    }

    @MainThread
    fun onLinkReceived(uri: Uri) {
        callbacks.filter { it.test(uri) }.forEach { it.onReceived(uri) }
    }

    private inner class DestroyObserver(private val callback: LinkCallback) : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            callbacks.remove(callback)
        }
    }

}