package orwir.gazzit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import orwir.gazzit.authorization.libAuthorizationModule
import orwir.gazzit.core.KoinLoger
import orwir.gazzit.core.libCoreModule
import orwir.gazzit.listing.libListingModule
import timber.log.Timber

class GazzitApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@GazzitApplication)
            logger(KoinLoger())
            modules(
                listOf(
                    appModule,

                    libCoreModule,
                    libAuthorizationModule,
                    libListingModule
                )
            )
        }
    }

}