package orwir.gazzit

import android.content.Context
import androidx.navigation.NavController
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module
import orwir.gazzit.authorization.AuthorizationNavigation
import orwir.gazzit.common.GlobalNavigation
import orwir.gazzit.common.HeadersInterceptor
import orwir.gazzit.feed.FeedNavigation
import orwir.gazzit.splash.SplashNavigation

val appModule = module {

    scope(named<MainActivity>()) {
        scoped { (context: Context, controller: NavController) ->
            Navigator(context, controller)
        } binds arrayOf(
            AuthorizationNavigation::class,
            SplashNavigation::class,
            FeedNavigation::class,
            GlobalNavigation::class
        )
    }

    single<HeadersInterceptor> {
        RedditHeadersInterceptor()
    }

}