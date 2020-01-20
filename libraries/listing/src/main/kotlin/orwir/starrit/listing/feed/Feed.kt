package orwir.starrit.listing.feed

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import orwir.starrit.core.model.ActionHolder
import orwir.starrit.core.model.NetworkState
import java.io.Serializable
import java.util.*

internal const val HOME = "/r/home"
internal const val POPULAR = "/r/popular"
internal const val ALL = "/r/all"

data class Feed(
    val posts: LiveData<PagedList<Post>>,
    val networkState: LiveData<NetworkState>,
    val retry: ActionHolder
) {
    sealed class Type(val subreddit: String, val solo: Boolean = false) : Serializable {
        object Home : Type(HOME)
        object Popular : Type(POPULAR)
        object All : Type(ALL)
        class Subreddit(name: String) : Type("/r/$name", true)

        fun asParameter() = if (this is Home) "" else subreddit

        override fun toString(): String = subreddit.substring(3)
    }

    enum class Sort {
        Best,
        Hot,
        New,
        Top,
        Rising;

        fun asParameter() = name.toLowerCase(Locale.ENGLISH)

        override fun toString(): String = asParameter()
    }
}