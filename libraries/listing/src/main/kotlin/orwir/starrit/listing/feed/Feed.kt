package orwir.starrit.listing.feed

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import orwir.starrit.core.model.ActionHolder
import orwir.starrit.core.model.NetworkState
import java.io.Serializable
import java.util.*

data class Feed(
    val posts: LiveData<PagedList<Post>>,
    val networkState: LiveData<NetworkState>,
    val retry: ActionHolder
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