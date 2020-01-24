package orwir.starrit.content.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubredditNames(val names: List<String>)