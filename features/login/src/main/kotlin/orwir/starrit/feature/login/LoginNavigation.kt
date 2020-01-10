package orwir.starrit.feature.login

import android.net.Uri

interface LoginNavigation {

    fun openBrowser(uri: Uri)

    fun openHomeFeed()

}