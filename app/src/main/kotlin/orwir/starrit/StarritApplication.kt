package orwir.starrit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import orwir.starrit.authorization.libraryAuthorizationModule
import orwir.starrit.core.KoinLogger
import orwir.starrit.feature.feed.featureFeedModule
import orwir.starrit.feature.login.featureLoginModule
import orwir.starrit.feature.splash.featureSplashModule
import orwir.starrit.listing.libraryListingModule
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
            modules(listOf(
                applicationModule,

                libraryAuthorizationModule,
                libraryListingModule,
                libraryViewModule,

                featureSplashModule,
                featureLoginModule,
                featureFeedModule
            ))
        }
    }

}