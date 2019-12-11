package orwir.gazzit.authorization

import orwir.gazzit.common.BuildConfig.REDDIT_BASE_URL
import orwir.gazzit.model.Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

private const val TOKEN_URL = "$REDDIT_BASE_URL/api/v1/access_token"
private const val BASIC_AUTH = "Authorization: Basic ${BuildConfig.CREDENTIALS_B64}"

internal interface AuthorizationService {

    @FormUrlEncoded
    @Headers(BASIC_AUTH)
    @POST(TOKEN_URL)
    suspend fun accessToken(
        @Field("code") code: String,
        @Field("grant_type") type: String = "authorization_code",
        @Field("redirect_uri") uri: String = BuildConfig.REDIRECT_URI
    ): Token

    @FormUrlEncoded
    @Headers(BASIC_AUTH)
    @POST(TOKEN_URL)
    suspend fun refreshToken(
        @Field("refresh_token") token: String,
        @Field("grant_type") type: String = "refresh_token"
    ): Token

}