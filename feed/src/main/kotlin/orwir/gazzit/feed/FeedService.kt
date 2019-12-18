package orwir.gazzit.feed

import orwir.gazzit.model.listing.Submissions
import orwir.gazzit.model.listing.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface FeedService {

    @GET("/best")
    suspend fun best(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("limit") limit: Int = FEED_ITEMS_LIMIT,
        @Query("include_categories") includeCategories: Boolean = true,
        @Query("raw_json") rawJson: Int = 1,
        @Query("sr_detail") srDetail: Boolean = true
    ): Response<Submissions>

    @GET("/r/{subreddit}/{type}")
    suspend fun subreddit(
        @Path("subreddit") subreddit: String,
        @Path("type") type: String = "hot",
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("limit") limit: Int = FEED_ITEMS_LIMIT,
        @Query("include_categories") includeCategories: Boolean = true,
        @Query("raw_json") rawJson: Int = 1,
        @Query("sr_detail") srDetail: Boolean = true
    ): Response<Submissions>

}