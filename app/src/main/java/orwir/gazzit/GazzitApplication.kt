package orwir.gazzit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import orwir.gazzit.auth.authorizationModule
import orwir.gazzit.source.localSourceModule
import orwir.gazzit.source.remote.remoteSourceModule
import orwir.gazzit.ui.authScreenModule
import orwir.gazzit.ui.feedScreenModule
import orwir.gazzit.ui.itemScreenModule
import orwir.gazzit.ui.splashScreenModule

class GazzitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GazzitApplication)
            modules(
                listOf(
                    remoteSourceModule,
                    localSourceModule,
                    authorizationModule,
                    splashScreenModule,
                    authScreenModule,
                    feedScreenModule,
                    itemScreenModule
                )
            )
        }
    }
}