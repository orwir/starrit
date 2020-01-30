package orwir.starrit.authorization

import android.net.Uri

interface AuthorizationNavigation {
    fun openDefaultFeed()
    fun openBrowser(uri: Uri)
}