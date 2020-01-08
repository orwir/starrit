package orwir.starrit.listing.feed

import java.io.Serializable
import java.util.*

data class Feed(
    val type: Type,
    val sort: Sort,
    val posts: List<Post>,
    val before: String?,
    val after: String?
) {

    sealed class Type(val subreddit: String) : Serializable {
        object Home : Type("")
        object Popular : Type("/r/popular")
        object All : Type("/r/all")
        class Subreddit(name: String) : Type("/r/$name")
    }

    enum class Sort {
        Best,
        Hot,
        New,
        Top,
        Rising;

        fun asParameter() = name.toLowerCase(Locale.ENGLISH)
    }

}