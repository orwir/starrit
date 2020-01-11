package orwir.starrit.feature.feed

import android.net.Uri

interface FeedNavigation {

    fun openBrowser(uri: Uri)

    fun openAuthorization()

}