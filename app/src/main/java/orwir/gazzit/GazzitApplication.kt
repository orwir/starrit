package orwir.gazzit

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import orwir.gazzit.authorization.authorizationModule
import orwir.gazzit.profile.profileModule
import orwir.gazzit.splash.splashModule

val commonModule = module {

    single<SharedPreferences> {
        get<Application>().getSharedPreferences("GazzitApplication", Context.MODE_PRIVATE)
    }

}

class GazzitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GazzitApplication)
            modules(
                listOf(
                    commonModule,
                    networkModule,
                    splashModule,
                    authorizationModule,
                    profileModule
                )
            )
        }
    }
}