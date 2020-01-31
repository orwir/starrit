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
import orwir.starrit.connect.ConnectFragmentDirections
import orwir.starrit.connect.ConnectNavigation
import orwir.starrit.container.ContentNavigation
import orwir.starrit.main.MainActivity
import orwir.starrit.splash.SplashFragmentDirections
import orwir.starrit.splash.SplashNavigation

internal class MainNavigator(
    private val activity: MainActivity
) : SplashNavigation, ConnectNavigation, ContentNavigation {

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
        navigator.navigate(ConnectFragmentDirections.openContentScreen())
    }

    override fun openLatestFeed() {
        navigator.navigate(SplashFragmentDirections.openContentScreen())
    }

    //    override fun openBrowser(uri: Uri) {
//        CustomTabsIntent
//            .Builder()
//            .build()
//            .launchUrl(context, uri)
//    }
//
//    override fun openLastFeed() {
//        val type = feedPreferences.latestType
//        val sort = feedPreferences.latestSort
//        val direction = SplashFragmentDirections.toFeed(type, sort)
//        controller.navigate(direction)
//    }
//
//    override fun openLogin() {
//        controller.navigate(SplashFragmentDirections.toLoginFragment())
//    }
//
//    override fun openHomeFeed() {
//        val direction = LoginFragmentDirections.toFeed(Type.Home, Sort.Best)
//        controller.navigate(direction)
//    }
//
//    override fun openAuthorization() {
//        controller.navigate(NavGraphDirections.toAuthorization())
//    }
//
//    override fun openFullscreen(post: Post) {
//        // todo: #45
//    }

}