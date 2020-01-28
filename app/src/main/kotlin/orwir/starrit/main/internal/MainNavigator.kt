package orwir.starrit.main.internal

import androidx.navigation.NavController
import androidx.navigation.findNavController
import orwir.starrit.R
import orwir.starrit.content.feed.FeedPreferences
import orwir.starrit.main.MainActivity
import orwir.starrit.splash.SplashNavigation

internal class MainNavigator(
    private val activity: MainActivity,
    private val feedPreferences: FeedPreferences
) : SplashNavigation {

    private val navigator: NavController by lazy { activity.findNavController(R.navigation.graph_main) }

    override fun openAuthorization() {

    }

    override fun openLatestFeed() {

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