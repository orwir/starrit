package orwir.starrit.listing.internal

import orwir.starrit.listing.model.Response
import orwir.starrit.listing.model.Submissions
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ListingService {

    @GET("/best")
    suspend fun best(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("include_categories") includeCategories: Boolean = true,
        @Query("sr_detail") srDetail: Boolean = true,
        @Query("raw_json") rawJson: Int = 1
    ): Response<Submissions>

    @GET("/r/{subreddit}/{type}")
    suspend fun subreddit(
        @Path("subreddit") subreddit: String,
        @Path("type") type: String = "hot",
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("include_categories") includeCategories: Boolean = true,
        @Query("sr_detail") srDetail: Boolean = true,
        @Query("raw_json") rawJson: Int = 1
    ): Response<Submissions>

}