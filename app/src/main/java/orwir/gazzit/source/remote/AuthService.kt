package orwir.gazzit.source.remote

import orwir.gazzit.auth.CLIENT_ID64
import orwir.gazzit.auth.REDIRECT_URI
import orwir.gazzit.auth.Token
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

private const val GRANT_TYPE = "authorization_code"

interface AuthService {
    @FormUrlEncoded
    @Headers("Authorization: Basic $CLIENT_ID64")
    @POST("/api/v1/access_token")
    fun accessToken(
        @Field("code") code: String,
        @Field("grant_type") type: String = GRANT_TYPE,
        @Field("redirect_uri") uri: String = REDIRECT_URI
    ): Call<Token>
}