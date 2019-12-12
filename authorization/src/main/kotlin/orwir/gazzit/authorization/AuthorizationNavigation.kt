package orwir.gazzit.authorization

import android.net.Uri
import orwir.gazzit.model.ListingType

interface AuthorizationNavigation {
    fun openBrowser(uri: Uri)
    fun openFeed(type: ListingType? = null)
}