package orwir.gazzit

import android.content.Context
import androidx.navigation.NavController
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module
import orwir.gazzit.core.AuthorizationInterceptor
import orwir.gazzit.feature.feed.FeedNavigation
import orwir.gazzit.feature.login.LoginNavigation
import orwir.gazzit.feature.splash.SplashNavigation
import orwir.gazzit.listing.adaper.KindAdapter
import orwir.gazzit.listing.adaper.VoteAdapter
import orwir.gazzit.network.CoreInterceptor
import orwir.gazzit.network.NetworkLoggingInterceptor

val appModule = module {

    scope(named<MainActivity>()) {
        scoped { (context: Context, controller: NavController) ->
            Navigator(context, controller)
        } binds arrayOf(
            SplashNavigation::class,
            LoginNavigation::class,
            FeedNavigation::class
        )
    }

    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(KindAdapter())
            .add(VoteAdapter())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(CoreInterceptor())
            .addInterceptor(get<AuthorizationInterceptor>())
            .addInterceptor(
                NetworkLoggingInterceptor(
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                )
            )
            .build()
    }

}