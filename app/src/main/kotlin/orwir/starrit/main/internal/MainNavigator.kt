package orwir.starrit.main.internal

import android.net.Uri
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import orwir.starrit.NavMainDirections
import orwir.starrit.R
import orwir.starrit.feature.connect.ConnectFragmentDirections
import orwir.starrit.feature.connect.ConnectNavigation
import orwir.starrit.feature.container.ContainerNavigation
import orwir.starrit.content.feed.Feed
import orwir.starrit.content.feed.FeedPreferences
import orwir.starrit.main.MainActivity
import orwir.starrit.feature.splash.SplashFragmentDirections
import orwir.starrit.feature.splash.SplashNavigation

internal class MainNavigator(
    private val activity: MainActivity,
    private val feedPrefs: FeedPreferences
) : SplashNavigation, ConnectNavigation, ContainerNavigation {

    private val navigator: NavController by lazy { activity.findNavController(R.id.navhost) }

    override fun openBrowser(uri: Uri) {
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(activity, uri)
    }

    override fun openAuthorization(clear: Boolean, vararg shared: Pair<View, String>) {
        val direction = NavMainDirections.openAuthorization()
        val options = NavOptions.Builder()
            .apply {
                if (clear) {
                    setPopUpTo(R.id.nav_main, true)
                    setEnterAnim(R.anim.fragment_fade_enter)
                    setExitAnim(R.anim.fragment_fade_exit)
                }
            }
            .build()
        navigator.navigate(direction.actionId, direction.arguments, options, FragmentNavigatorExtras(*shared))
    }

    override fun openDefaultFeed() {
        navigator.navigate(ConnectFragmentDirections.openFeed(Feed.Type.Home, Feed.Sort.Best))
    }

    override fun openLatestFeed() {
        val direction = SplashFragmentDirections.openFeed(feedPrefs.latestType, feedPrefs.latestSort)
        navigator.navigate(direction)
    }

}