package orwir.gazzit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import orwir.gazzit.authorization.authorizationModule
import orwir.gazzit.common.commonModule
import orwir.gazzit.common.localModule
import orwir.gazzit.common.remoteModule
import orwir.gazzit.listing.listingModule
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
                    commonModule,
                    localModule,
                    remoteModule,
                    splashModule,
                    authorizationModule,
                    listingModule,
                    profileModule
                )
            )
        }
    }
}