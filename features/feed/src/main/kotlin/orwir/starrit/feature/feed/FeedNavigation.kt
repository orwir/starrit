package orwir.starrit.feature.feed

import android.net.Uri
import orwir.starrit.content.post.PostNavigation

interface FeedNavigation : PostNavigation {

    override fun openBrowser(uri: Uri)

    fun openAuthorization()

}