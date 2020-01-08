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
        object Home : Type("/r/home")
        object Popular : Type("/r/popular")
        object All : Type("/r/all")
        class Subreddit(name: String) : Type("/r/$name")

        fun asParameter() = if (this is Home) "" else subreddit
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