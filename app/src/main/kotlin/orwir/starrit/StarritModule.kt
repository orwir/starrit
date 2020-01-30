package orwir.starrit

import android.app.Application
import android.content.SharedPreferences
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import orwir.starrit.access.AccessInterceptor
import orwir.starrit.content.internal.adapter.FeedTypeAdapter
import orwir.starrit.content.internal.adapter.KindAdapter
import orwir.starrit.content.internal.adapter.VoteAdapter
import orwir.starrit.main.internal.MainNetworkInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val starritModule = module {

    single<SharedPreferences> {
        val app: Application = get()
        app.getSharedPreferences(app::class.qualifiedName, Application.MODE_PRIVATE)
    }

    factory<ExoPlayer> {
        SimpleExoPlayer.Builder(get())
            .setUseLazyPreparation(true)
            .build()
    }

    single {
        OkHttpClient.Builder()
            .apply {
                addInterceptor(MainNetworkInterceptor())
                addInterceptor(get<AccessInterceptor>())
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                }
            }
            .build()
    }

    single {
        Moshi.Builder()
            .add(KindAdapter())
            .add(VoteAdapter())
            .add(FeedTypeAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("http://localhost/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

}