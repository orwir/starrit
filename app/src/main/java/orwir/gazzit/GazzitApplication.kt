package orwir.gazzit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import orwir.gazzit.authorization.authorizationModule
import orwir.gazzit.feed.feedModule
import orwir.gazzit.profile.profileModule
import orwir.gazzit.splash.splashModule

class GazzitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GazzitApplication)
            modules(
                listOf(
                    localModule,
                    networkModule,
                    splashModule,
                    authorizationModule,
                    feedModule,
                    profileModule
                )
            )
        }
    }
}