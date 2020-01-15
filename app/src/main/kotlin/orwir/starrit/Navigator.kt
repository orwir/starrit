package orwir.starrit

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.NavController
import orwir.starrit.feature.feed.FeedNavigation
import orwir.starrit.feature.feed.FeedPreferences
import orwir.starrit.feature.login.LoginFragmentDirections
import orwir.starrit.feature.login.LoginNavigation
import orwir.starrit.feature.splash.SplashFragmentDirections
import orwir.starrit.feature.splash.SplashNavigation
import orwir.starrit.listing.feed.Feed

class Navigator(
    private val context: Context,
    private val controller: NavController,
    private val feedPreferences: FeedPreferences
) : SplashNavigation, LoginNavigation, FeedNavigation {

    override fun openBrowser(uri: Uri) {
        CustomTabsIntent
            .Builder()
            .build()
            .launchUrl(context, uri)
    }

    override fun openLastFeed() {
        val type = feedPreferences.latestType
        val sort = feedPreferences.latestSort
        val direction = SplashFragmentDirections.toFeed(type, sort)
        controller.navigate(direction)
    }

    override fun openLogin() {
        controller.navigate(SplashFragmentDirections.toLoginFragment())
    }

    override fun openHomeFeed() {
        val direction = LoginFragmentDirections.toFeed(Feed.Type.Home, Feed.Sort.Best)
        controller.navigate(direction)
    }

    override fun openAuthorization() {
        controller.navigate(NavGraphDirections.toAuthorization())
    }
}