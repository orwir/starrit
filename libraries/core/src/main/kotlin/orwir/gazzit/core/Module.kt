package orwir.gazzit.core

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import coil.ImageLoader
import coil.decode.GifDecoder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.logger.KOIN_TAG
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

val libCoreModule = module {

    single<SharedPreferences> {
        val app: Application = get()
        app.getSharedPreferences(app::class.qualifiedName, Application.MODE_PRIVATE)
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.REDDIT_URL_OAUTH)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single<ImageLoader> {
        ImageLoader(get()) {
            componentRegistry {
                add(GifDecoder())
            }
        }
    }

}

class KoinLoger : Logger() {
    override fun log(level: Level, msg: MESSAGE) {
        val priority = when (level) {
            Level.DEBUG -> Log.DEBUG
            Level.ERROR -> Log.ERROR
            Level.INFO -> Log.INFO
        }
        Timber.tag(KOIN_TAG).log(priority, msg)
    }
}