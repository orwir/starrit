package orwir.gazzit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import orwir.gazzit.authorization.authorizationModule
import orwir.gazzit.common.KoinedLogger
import orwir.gazzit.common.commonModule
import orwir.gazzit.feed.feedModule
import orwir.gazzit.splash.splashModule
import timber.log.Timber

class GazzitApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            logger(KoinedLogger)
            androidContext(this@GazzitApplication)
            modules(
                listOf(
                    commonModule,
                    splashModule,
                    authorizationModule,
                    feedModule,
                    appModule
                )
            )
        }
    }
}