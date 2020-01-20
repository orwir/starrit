package orwir.starrit.feature.feed.internal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import orwir.starrit.core.model.NetworkState
import orwir.starrit.feature.feed.R
import orwir.starrit.feature.feed.databinding.ViewNetworkStateBinding
import orwir.starrit.feature.feed.databinding.ViewPostBinding
import orwir.starrit.feature.feed.internal.content.PostContentBinder
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.Post

internal class FeedAdapter(
    private val type: Feed.Type,
    private val contentBinder: PostContentBinder,
    private val retryHandler: () -> Unit
) : PagedListAdapter<Post, RecyclerView.ViewHolder>(PostDiffCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.view_post -> createPostViewHolder(parent)
            R.layout.view_network_state -> createNetworkStateViewHolder(parent)
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> getItem(position)?.let(holder::bind)
            is NetworkStateViewHolder -> holder.bind(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (hasExtraRow() && position == itemCount - 1) {
            R.layout.view_network_state
        } else {
            R.layout.view_post
        }

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

    private fun createPostViewHolder(parent: ViewGroup) = ViewPostBinding
        .inflate(LayoutInflater.from(parent.context), parent, false)
        .let { PostViewHolder(it, contentBinder) }

    private fun createNetworkStateViewHolder(parent: ViewGroup) = ViewNetworkStateBinding
        .inflate(LayoutInflater.from(parent.context), parent, false)
        .let { NetworkStateViewHolder(it, retryHandler) }

}