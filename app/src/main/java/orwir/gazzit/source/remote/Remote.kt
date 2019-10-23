package orwir.gazzit.source.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import orwir.gazzit.auth.AuthInterceptor
import orwir.gazzit.auth.CLIENT_ID64
import orwir.gazzit.auth.REDIRECT_URI
import orwir.gazzit.auth.Token
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

val remoteSourceModule = module {
    single { buildRetrofit(get()) }
    single { createService(get(), AuthService::class.java) }
    single { createService(get(), FeedService::class.java) }
}

internal fun buildMoshi(): Moshi =
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

internal fun buildOkHttp(authInterceptor: AuthInterceptor): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

internal fun buildRetrofit(authInterceptor: AuthInterceptor): Retrofit =
    Retrofit.Builder()
        .client(buildOkHttp(authInterceptor))
        .baseUrl("https://oauth.reddit.com")
        .addConverterFactory(MoshiConverterFactory.create(buildMoshi()))
        .build()

internal fun <T> createService(retrofit: Retrofit, service: Class<T>): T = retrofit.create(service)