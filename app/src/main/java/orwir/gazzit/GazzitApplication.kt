package orwir.gazzit

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import orwir.gazzit.authorization.authorizationModule
import orwir.gazzit.profile.profileModule

@ExperimentalCoroutinesApi
class GazzitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GazzitApplication)
            modules(
                listOf(
                    networkModule,
                    authorizationModule,
                    profileModule
                )
            )
        }
    }
}