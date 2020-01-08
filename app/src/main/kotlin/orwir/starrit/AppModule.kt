package orwir.starrit

import android.content.Context
import androidx.navigation.NavController
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module
import orwir.starrit.authorization.AuthorizationInterceptor
import orwir.starrit.feature.feed.FeedNavigation
import orwir.starrit.feature.login.LoginNavigation
import orwir.starrit.feature.splash.SplashNavigation
import orwir.starrit.listing.adapter.KindAdapter
import orwir.starrit.listing.adapter.VoteAdapter
import orwir.starrit.network.CoreInterceptor
import orwir.starrit.network.NetworkLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("http://localhost/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
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