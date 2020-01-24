package orwir.starrit.content.feed

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import orwir.starrit.content.post.Post
import orwir.starrit.core.model.ActionHolder
import orwir.starrit.core.model.NetworkState
import orwir.starrit.core.util.createHashCode
import java.io.Serializable
import java.util.*

internal const val HOME = "/r/home"
internal const val POPULAR = "/r/popular"
internal const val ALL = "/r/all"

class Feed internal constructor(
    val posts: LiveData<PagedList<Post>>,
    val networkState: LiveData<NetworkState>,
    val retry: ActionHolder
) {

    sealed class Type(val path: String, val composite: Boolean = true) : Serializable {
        object Home : Type(HOME)
        object Popular : Type(POPULAR)
        object All : Type(ALL)
        class Subreddit(name: String) : Type("/r/$name", false) {
            override fun equals(other: Any?) = other is Subreddit && path == other.path
            override fun hashCode() = createHashCode(path)
        }

        override fun toString(): String = this::class.simpleName ?: path

        internal fun asParameter() = if (this is Home) "" else path
    }

    enum class Sort {
        Best,
        Hot,
        New,
        Top,
        Rising;

        internal fun asParameter() = name.toLowerCase(Locale.ENGLISH)
    }

}