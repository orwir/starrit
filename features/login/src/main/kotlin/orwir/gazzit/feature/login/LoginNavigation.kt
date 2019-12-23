package orwir.gazzit.feature.login

import android.net.Uri

interface LoginNavigation {
    fun openBrowser(uri: Uri)
    fun openHomePage()
}