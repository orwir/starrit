package orwir.gazzit

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import orwir.gazzit.authorization.authorizationModule
import orwir.gazzit.common.networkModule

@ExperimentalCoroutinesApi
class GazzitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GazzitApplication)
            modules(
                listOf(
                    networkModule,
                    authorizationModule
                )
            )
        }
    }
}