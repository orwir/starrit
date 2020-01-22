package orwir.starrit.listing.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Suggest(val names: List<String>)