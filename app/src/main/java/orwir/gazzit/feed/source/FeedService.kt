package orwir.gazzit.feed.source

import orwir.gazzit.feed.model.Listing
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedService {

    @GET("/best")
    suspend fun best(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("limit") limit: Int = 15
    ): Listing

    @GET("/r/{subreddit}/hot")
    suspend fun hot(
        @Path("subreddit") subreddit: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("limit") limit: Int = 15
    ): Listing

}