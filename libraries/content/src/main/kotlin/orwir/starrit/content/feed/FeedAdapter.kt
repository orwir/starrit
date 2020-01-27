package orwir.starrit.content.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import orwir.starrit.content.databinding.ViewNetworkStateBinding
import orwir.starrit.content.internal.feed.NetworkStateViewHolder
import orwir.starrit.content.internal.post.PostContentBinder
import orwir.starrit.content.internal.post.PostDiffCallback
import orwir.starrit.content.internal.post.PostViewHolder
import orwir.starrit.content.internal.post.PostViewWrapper
import orwir.starrit.content.post.Post
import orwir.starrit.core.model.NetworkState

private const val POST_VIEW = 1
private const val NETWORK_VIEW = 2

class FeedAdapter(
    private val type: Feed.Type,
    private val contentBinder: PostContentBinder,
    private val retryHandler: () -> Unit
) : PagedListAdapter<Post, RecyclerView.ViewHolder>(PostDiffCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            POST_VIEW -> createPostViewHolder(parent)
            NETWORK_VIEW -> createNetworkStateViewHolder(parent)
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> getItem(position)?.let(holder::bind)
            is NetworkStateViewHolder -> holder.bind(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (hasExtraRow() && position == itemCount - 1) NETWORK_VIEW else POST_VIEW

    override fun getItemCount(): Int = super.getItemCount() + if (hasExtraRow()) 1 else 0

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState !is NetworkState.Success

    private fun createPostViewHolder(parent: ViewGroup) =
        PostViewHolder(PostViewWrapper(LayoutInflater.from(parent.context), parent, false, type), contentBinder)

    private fun createNetworkStateViewHolder(parent: ViewGroup) = ViewNetworkStateBinding
        .inflate(LayoutInflater.from(parent.context), parent, false)
        .let { NetworkStateViewHolder(it, retryHandler) }

}