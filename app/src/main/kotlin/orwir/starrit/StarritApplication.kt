package orwir.starrit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import orwir.starrit.access.libraryAccessModule
import orwir.starrit.connect.featureConnectModule
import orwir.starrit.content.libraryContentModule
import orwir.starrit.core.KoinLogger
import orwir.starrit.main.mainModule
import orwir.starrit.splash.featureSplashModule
import orwir.starrit.view.libraryViewModule
import timber.log.Timber

class StarritApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@StarritApplication)
            logger(KoinLogger())
            modules(
                listOf(
                    starritModule,
                    mainModule,

                    libraryAccessModule,
                    libraryContentModule,
                    libraryViewModule,

                    featureSplashModule,
                    featureConnectModule
                )
            )
        }
    }

}