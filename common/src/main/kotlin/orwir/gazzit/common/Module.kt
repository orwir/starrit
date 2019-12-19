package orwir.gazzit.common

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.util.CoilUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import orwir.gazzit.common.json.KindAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val commonModule = module {

    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(KindAdapter())
            .build()
    }

    single<SharedPreferences> {
        PreferenceManager.getDefaultSharedPreferences(get())
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(RedditInterceptor())
            .addInterceptor(get<AuthorizationInterceptor>())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.REDDIT_AUTH_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single<ImageLoader> {
        ImageLoader(get()) {
            okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(get()))
                    .build()
            }
            componentRegistry {
                add(GifDecoder())
            }
        }
    }

}