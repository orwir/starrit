package orwir.starrit

import android.app.Application
import androidx.navigation.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module
import orwir.starrit.access.AccessInterceptor
import orwir.starrit.feature.feed.FeedNavigation
import orwir.starrit.feature.login.LoginNavigation
import orwir.starrit.feature.splash.SplashNavigation
import orwir.starrit.listing.adapter.FeedTypeAdapter
import orwir.starrit.listing.adapter.KindAdapter
import orwir.starrit.listing.adapter.VoteAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val applicationModule = module {

    single {
        val app: Application = get()
        app.getSharedPreferences(app::class.qualifiedName, Application.MODE_PRIVATE)
    }

    single<ExoPlayer> {
        SimpleExoPlayer.Builder(get())
            .setUseLazyPreparation(true)
            .build()
    }

    single {
        OkHttpClient.Builder()
            .apply {
                addInterceptor(ApplicationInterceptor())
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

    scope(named<MainActivity>()) {
        scoped {
            val activity = get<MainActivity>()
            Navigator(activity, activity.findNavController(R.id.navhost), get())
        } binds arrayOf(
            SplashNavigation::class,
            LoginNavigation::class,
            FeedNavigation::class
        )
    }

}