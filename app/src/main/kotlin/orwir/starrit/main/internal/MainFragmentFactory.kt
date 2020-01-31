package orwir.starrit.main.internal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import org.koin.core.scope.Scope
import orwir.starrit.feature.connect.ConnectFragment
import orwir.starrit.feature.container.ContainerFragment
import orwir.starrit.feature.splash.SplashFragment

internal class MainFragmentFactory(private val scope: Scope) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            SplashFragment::class.java -> SplashFragment(scope.get())
            ConnectFragment::class.java -> ConnectFragment(scope.get())
            ContainerFragment::class.java -> ContainerFragment(scope.get())
            else -> super.instantiate(classLoader, className)
        }

}