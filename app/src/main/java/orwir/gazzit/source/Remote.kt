package orwir.gazzit.source

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
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
    single { buildRetrofit() }
    single { createService(get(), AuthService::class.java) }
}

const val REDDIT_URL = "https://www.reddit.com"
const val REDDIT_OAUTH_URL = "https://oauth.reddit.com"

fun buildMoshi(): Moshi =
    Moshi.Builder()
        .build()

fun buildOkHttp(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

fun buildRetrofit(): Retrofit =
    Retrofit.Builder()
        .client(buildOkHttp())
        .baseUrl(REDDIT_URL)
        .addConverterFactory(MoshiConverterFactory.create(buildMoshi()))
        .build()

fun <T> createService(retrofit: Retrofit, service: Class<T>): T = retrofit.create(service)

interface AuthService {
    @FormUrlEncoded
    @Headers("Authorization: Basic $CLIENT_ID64")
    @POST("/api/v1/access_token")
    fun accessToken(
        @Field("code") code: String,
        @Field("grant_type") type: String = "authorization_code",
        @Field("redirect_uri") uri: String = REDIRECT_URI
    ): Call<Token>
}