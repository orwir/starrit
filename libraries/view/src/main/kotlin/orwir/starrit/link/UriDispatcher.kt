package orwir.starrit.link

import android.net.Uri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import java.util.*

class UriDispatcher {

    private val callbacks = ArrayDeque<UriCallback>()

    fun addCallback(owner: LifecycleOwner, builder: UriCallbackBuilder.() -> Unit) {
        if (owner.lifecycle.currentState != Lifecycle.State.DESTROYED) {
            UriCallbackBuilder()
                .apply {
                    lifecycle = owner.lifecycle
                    onDestroy = ::onDestroyOwner
                }
                .apply(builder)
                .build()
                .let { callbacks.add(it) }
        }
    }

    fun onUri(uri: Uri) {
        callbacks.filter { it.predicate(uri) }.forEach { it.onUri(uri) }
    }

    private fun onDestroyOwner(callback: UriCallback) {
        callbacks.remove(callback)
    }

}