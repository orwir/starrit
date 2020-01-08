package orwir.starrit.listing.internal

import orwir.starrit.listing.model.Listing
import orwir.starrit.listing.model.Thing
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ListingService {

    @GET("{base}{subreddit}/{sort}.json")
    suspend fun listing(
        @Path("base", encoded = true) base: String,
        @Path("subreddit", encoded = true) subreddit: String,
        @Path("sort", encoded = true) sort: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("include_categories") withCategories: Boolean = true,
        @Query("sr_detail") details: Boolean = true,
        @Query("raw_json") raw: Int = 1
    ): Thing<Listing>

}