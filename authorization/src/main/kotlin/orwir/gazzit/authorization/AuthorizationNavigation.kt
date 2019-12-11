package orwir.gazzit.authorization

import android.net.Uri

interface AuthorizationNavigation {
    fun openAuthorizationUri(uri: Uri)
    fun openFeed()
}