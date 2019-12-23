package orwir.gazzit.feature.feed

import android.net.Uri

interface FeedNavigation {
    fun openBrowser(uri: Uri)
}