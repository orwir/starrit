package orwir.starrit.content.post

import android.net.Uri

interface PostNavigation {

    fun openBrowser(uri: Uri)

    fun openFullscreen(post: Post)

}