package orwir.gazzit.common

import android.net.Uri

interface GlobalNavigation {
    fun openBrowser(uri: Uri)
}