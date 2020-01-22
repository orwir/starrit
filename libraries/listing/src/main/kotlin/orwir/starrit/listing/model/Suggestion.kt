package orwir.starrit.listing.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Suggestion(val names: List<String>)