package orwir.gazzit

import android.content.Context
import androidx.navigation.NavController
import org.koin.core.qualifier.named
import org.koin.dsl.module
import orwir.gazzit.authorization.AuthorizationNavigation
import orwir.gazzit.feed.FeedNavigation
import orwir.gazzit.splash.SplashNavigation

val appModule = module {

    factory<AuthorizationNavigation> {
        get<Navigator>().authorizationNavigation
    }

    factory<SplashNavigation> {
        get<Navigator>().splashNavigation
    }

    factory<FeedNavigation> {
        get<Navigator>().feedNavigation
    }

//    scope(named<MainActivity>()) {
//        scoped { (context: Context, navigator: NavController) -> Navigator(context, navigator) }
//    }

}