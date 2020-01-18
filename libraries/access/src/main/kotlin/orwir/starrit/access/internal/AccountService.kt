package orwir.starrit.access.internal

import orwir.starrit.core.BuildConfig.REDDIT_URL_OAUTH
import retrofit2.http.GET
import retrofit2.http.Query

internal interface AccountService {

    @GET("$REDDIT_URL_OAUTH/api/v1/me")
    suspend fun me(@Query("raw_json") raw: Int = 1): Any

    @GET("$REDDIT_URL_OAUTH/api/v1/me/prefs")
    suspend fun prefs(@Query("raw_json") raw: Int = 1): Any

}