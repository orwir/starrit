package orwir.starrit.core

import android.app.Application
import android.content.SharedPreferences
import coil.ImageLoader
import coil.decode.GifDecoder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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