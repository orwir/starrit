package orwir.starrit.main

import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module
import orwir.starrit.connect.ConnectNavigation
import orwir.starrit.container.ContainerNavigation
import orwir.starrit.main.internal.MainNavigator
import orwir.starrit.splash.SplashNavigation

val mainModule = module {

    scope(named<MainActivity>()) {
        scoped { MainNavigator(get(), get()) } binds arrayOf(
            SplashNavigation::class,
            ConnectNavigation::class,
            ContainerNavigation::class
        )
    }

}