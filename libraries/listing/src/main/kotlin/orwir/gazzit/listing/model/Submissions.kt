package orwir.gazzit.listing.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Submissions(
    val before: String?,
    val after: String?,
    val modhash: String?,
    val children: List<Response<Submission>>
)