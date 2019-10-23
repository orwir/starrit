package orwir.gazzit.source.remote

import retrofit2.Call
import retrofit2.http.GET

interface FeedService {

    @GET("/api/best")
    fun best(): Call<String>

}