package orwir.gazzit.listing.source

import orwir.gazzit.model.Listing
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ListingService {

    @GET("/best")
    suspend fun best(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("limit") limit: Int = 15
    ): Listing

    @GET("/r/{subreddit}/{type}")
    suspend fun subreddit(
        @Path("subreddit") subreddit: String,
        @Path("type") type: String = "hot",
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("limit") limit: Int = 15
    ): Listing

}