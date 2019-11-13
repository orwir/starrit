package orwir.gazzit.profile

import retrofit2.http.GET

interface ProfileService {

    @GET("/api/v1/me")
    suspend fun me(): Profile

}