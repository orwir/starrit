package orwir.starrit.feature.feed.internal

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.core.model.NetworkState
import orwir.starrit.feature.feed.BuildConfig
import orwir.starrit.feature.feed.R
import orwir.starrit.feature.feed.databinding.ViewNetworkStateBinding
import orwir.starrit.feature.feed.databinding.ViewPostBinding
import orwir.starrit.listing.feed.Post
import orwir.starrit.view.setVisibleOrGone

internal class FeedAdapter(
    private val onRetry: () -> Unit
) : PagedListAdapter<Post, RecyclerView.ViewHolder>(PostDiffCallback()) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
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
        .run(::PostViewHolder)

    private fun createNetworkStateViewHolder(parent: ViewGroup) = ViewNetworkStateBinding
        .inflate(LayoutInflater.from(parent.context), parent, false)
        .let { NetworkStateViewHolder(it, onRetry) }

}

internal class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
}

internal class PostViewHolder(
    private val binding: ViewPostBinding
) : RecyclerView.ViewHolder(binding.root), KoinComponent {

    private val inflater: LayoutInflater = LayoutInflater.from(itemView.context)
    private val content: ContentInflater by inject()

    fun bind(post: Post) {
        binding.post = post
        binding.content.removeAllViews()
        binding.content.addView(content.inflate(post, inflater))
        binding.share.setOnClickListener { share(post) }
    }

    private fun share(post: Post) {
        val content = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, post.contentUrl)
        }
        itemView.context.startActivity(Intent.createChooser(content, post.title))
    }

}

internal class NetworkStateViewHolder(
    private val binding: ViewNetworkStateBinding,
    private val onRetry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(networkState: NetworkState?) {
        binding.apply {
            loading.setVisibleOrGone(networkState is NetworkState.Loading)
            retry.setVisibleOrGone(networkState is NetworkState.Failure)
            retry.setOnClickListener { onRetry.invoke() }
            error.setVisibleOrGone(networkState is NetworkState.Failure)
            error.text = if (networkState is NetworkState.Failure) networkState.message() else ""
        }
    }

    private fun NetworkState.Failure.message() =
        if (BuildConfig.DEBUG) {
            error.message
        } else {
            itemView.context.getText(R.string.posts_loading_error)
        }

}