package orwir.starrit.link

import android.net.Uri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class UriCallbackBuilder {

    internal lateinit var lifecycle: Lifecycle
    internal lateinit var onDestroy: (callback: UriCallback) -> Unit
    private var predicate: (uri: Uri) -> Boolean = { true }
    private var callback: (uri: Uri) -> Unit = {}

    fun ifUri(predicate: (uri: Uri) -> Boolean) {
        this.predicate = predicate
    }

    fun ifUriStartsWith(start: String) {
        ifUri { it.toString().startsWith(start) }
    }

    fun onUri(callback: (uri: Uri) -> Unit) {
        this.callback = callback
    }

    internal fun build(): UriCallback {
        return UriCallback(predicate, callback, lifecycle, onDestroy)
    }
}

internal class UriCallback(
    val predicate: (uri: Uri) -> Boolean,
    private val callback: (uri: Uri) -> Unit,
    private val lifecycle: Lifecycle,
    private val onDestroy: (callback: UriCallback) -> Unit
) : LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    fun onUri(uri: Uri) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            callback(uri)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        lifecycle.removeObserver(this)
        onDestroy(this)
    }

}