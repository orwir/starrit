package orwir.gazzit.common

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val remoteModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthorizationInterceptor>())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(REDDIT_AUTH_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

}

fun <T> service(retrofit: Retrofit, service: Class<T>): T = retrofit.create(service)

const val REDDIT_BASE_URL = "https://www.reddit.com"
const val REDDIT_AUTH_URL = "https://oauth.reddit.com"