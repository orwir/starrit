package orwir.starrit.content.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Thing<T>(
    val id: String = "",
    val name: String = "",
    val kind: Kind,
    val data: T
)