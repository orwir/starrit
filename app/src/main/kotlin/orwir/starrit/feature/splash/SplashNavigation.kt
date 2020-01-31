package orwir.starrit.feature.splash

import android.view.View

interface SplashNavigation {
    fun openAuthorization(clear: Boolean = true, vararg shared: Pair<View, String>)
    fun openLatestFeed()
}