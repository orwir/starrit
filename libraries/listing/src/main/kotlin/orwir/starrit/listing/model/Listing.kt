package orwir.starrit.listing.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Listing(
    val before: String?,
    val after: String?,
    val children: List<Thing<Submission>>
)