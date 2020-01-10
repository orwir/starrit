package orwir.starrit.core.link

import android.net.Uri

class LinkCallbackBuilder {

    private lateinit var filter: (uri: Uri) -> Boolean
    private lateinit var callback: (uri: Uri) -> Unit

    fun filter(filter: (uri: Uri) -> Boolean) {
        this.filter = filter
    }

    fun onLinkReceived(callback: (uri: Uri) -> Unit) {
        this.callback = callback
    }

    internal fun build(): LinkCallback = LinkCallback(filter, callback)

}

data class LinkCallback(
    val test: (uri: Uri) -> Boolean,
    val onReceived: (uri: Uri) -> Unit
)