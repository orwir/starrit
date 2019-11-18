package orwir.gazzit.profile

import orwir.gazzit.model.Profile
import retrofit2.http.GET

interface ProfileService {

    @GET("/api/v1/me")
    suspend fun me(): Profile

}