package orwir.starrit.main.internal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import org.koin.core.scope.Scope
import orwir.starrit.connect.ConnectFragment
import orwir.starrit.container.ContentFragment
import orwir.starrit.splash.SplashFragment

internal class MainFragmentFactory(private val scope: Scope) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            SplashFragment::class.java -> SplashFragment(scope.get())
            ConnectFragment::class.java -> ConnectFragment(scope.get())
            ContentFragment::class.java -> ContentFragment(scope.get())
            else -> super.instantiate(classLoader, className)
        }

}