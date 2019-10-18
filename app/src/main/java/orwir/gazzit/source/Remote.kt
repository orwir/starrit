package orwir.gazzit.source

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import orwir.gazzit.auth.Token
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

val remoteSourceModule = module {
    single { buildRetrofit() }
    single { createService(get(), Authorization::class.java) }
}

const val REDDIT_URL = "https://www.reddit.com"
const val REDDIT_OAUTH_URL = "https://oauth.reddit.com"

fun buildMoshi(): Moshi =
    Moshi.Builder()
        .build()

fun buildOkHttp(): OkHttpClient =
    OkHttpClient.Builder()
        .build()

fun buildRetrofit(): Retrofit =
    Retrofit.Builder()
        .client(buildOkHttp())
        .baseUrl(REDDIT_URL)
        .addConverterFactory(MoshiConverterFactory.create(buildMoshi()))
        .build()

fun <T> createService(retrofit: Retrofit, service: Class<T>): T = retrofit.create(service)

interface Authorization {

    @FormUrlEncoded
    @POST("$REDDIT_OAUTH_URL/api/v1/access_token")
    fun accessToken(
        @Field("grant_type") type: String,
        @Field("code") code: String,
        @Field("redirect_uri") uri: String
    ): Token

}