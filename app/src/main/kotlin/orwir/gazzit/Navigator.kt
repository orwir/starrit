package orwir.gazzit

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.NavController
import orwir.gazzit.authorization.AuthorizationFragmentDirections
import orwir.gazzit.authorization.AuthorizationNavigation
import orwir.gazzit.feed.FeedNavigation
import orwir.gazzit.splash.SplashFragmentDirections
import orwir.gazzit.splash.SplashNavigation

class Navigator(
    private val context: Context,
    private val controller: NavController
) : SplashNavigation, AuthorizationNavigation, FeedNavigation {

    override fun openLatestFeed() {
        controller.navigate(SplashFragmentDirections.toListing())
    }

    override fun requestAuthorization() {
        controller.navigate(SplashFragmentDirections.toAuthorization())
    }

    override fun openBrowser(uri: Uri) {
        CustomTabsIntent
            .Builder()
            .build()
            .launchUrl(context, uri)
    }

    override fun openFeed() {
        controller.navigate(AuthorizationFragmentDirections.toListing())
    }

}