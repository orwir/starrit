package orwir.gazzit.authorization

import orwir.gazzit.authorization.model.Token
import orwir.gazzit.common.REDDIT_BASE_URL
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthorizationService {

    @FormUrlEncoded
    @Headers("Authorization: Basic $CREDENTIALS_B64")
    @POST("${REDDIT_BASE_URL}/api/v1/access_token")
    suspend fun accessToken(
        @Field("code") code: String,
        @Field("grant_type") type: String = GRANT_TYPE,
        @Field("redirect_uri") uri: String = REDIRECT_URI
    ): Token

}