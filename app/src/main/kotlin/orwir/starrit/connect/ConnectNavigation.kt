package orwir.starrit.connect

import android.net.Uri

interface ConnectNavigation {
    fun openDefaultFeed()
    fun openBrowser(uri: Uri)
}