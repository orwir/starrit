package orwir.gazzit

import android.app.Application
import android.util.Log
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.util.CoilUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.KOIN_TAG
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import orwir.gazzit.authorization.authorizationModule
import orwir.gazzit.common.commonModule
import orwir.gazzit.common.localModule
import orwir.gazzit.common.remoteModule
import orwir.gazzit.listing.listingModule
import orwir.gazzit.profile.profileModule
import orwir.gazzit.search.searchModule
import orwir.gazzit.splash.splashModule
import timber.log.Timber

class GazzitApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            logger(object : Logger() {
                override fun log(level: Level, msg: MESSAGE) {
                    val priority = when (level) {
                        Level.DEBUG -> Log.DEBUG
                        Level.ERROR -> Log.ERROR
                        Level.INFO -> Log.INFO
                    }
                    Timber.tag(KOIN_TAG).log(priority, msg)
                }
            })
            androidContext(this@GazzitApplication)
            modules(
                listOf(
                    commonModule,
                    localModule,
                    remoteModule,
                    splashModule,
                    authorizationModule,
                    listingModule,
                    searchModule,
                    profileModule
                )
            )
        }

        Coil.setDefaultImageLoader {
            ImageLoader(this@GazzitApplication) {
                crossfade(true)
                okHttpClient {
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                        .cache(CoilUtils.createDefaultCache(this@GazzitApplication))
                        .build()
                }
                componentRegistry {
                    add(GifDecoder())
                }
            }
        }
    }
}