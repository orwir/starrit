package orwir.starrit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import orwir.starrit.authorization.libAuthorizationModule
import orwir.starrit.core.KoinLoger
import orwir.starrit.core.libCoreModule
import orwir.starrit.listing.libListingModule
import timber.log.Timber

class StarritApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@StarritApplication)
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