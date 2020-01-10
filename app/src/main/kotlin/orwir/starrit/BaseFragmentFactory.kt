package orwir.starrit

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import org.koin.core.scope.Scope
import orwir.starrit.feature.feed.FeedFragment
import orwir.starrit.feature.login.LoginFragment
import orwir.starrit.feature.splash.SplashFragment

class BaseFragmentFactory(private val scope: Scope) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            SplashFragment::class.java -> SplashFragment(scope.inject())
            LoginFragment::class.java -> LoginFragment(scope.inject())
            FeedFragment::class.java -> FeedFragment(scope.inject(), scope.get())
            else -> super.instantiate(classLoader, className)
        }

}