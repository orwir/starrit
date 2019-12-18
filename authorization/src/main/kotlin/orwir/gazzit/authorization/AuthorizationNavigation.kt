package orwir.gazzit.authorization

import android.net.Uri

interface AuthorizationNavigation {
    fun openBrowser(uri: Uri)
    fun openFeed()
}