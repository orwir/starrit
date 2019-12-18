package orwir.gazzit.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import orwir.gazzit.feed.content.inflateImageContent
import orwir.gazzit.feed.content.inflateLinkContent
import orwir.gazzit.feed.databinding.ViewPostBinding
import orwir.gazzit.feed.model.ImagePost
import orwir.gazzit.feed.model.LinkPost
import orwir.gazzit.feed.model.Post

internal class FeedAdapter : PagedListAdapter<Post, ViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewPostBinding
        .inflate(LayoutInflater.from(parent.context), parent, false)
        .run(::ViewHolder)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

}

internal class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id // todo: check it
}

internal class ViewHolder(
    private val binding: ViewPostBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val inflater: LayoutInflater = LayoutInflater.from(itemView.context)

    fun bind(post: Post) {
        binding.post = post
        binding.content.removeAllViews()
        binding.content.addView(
            when (post) {
                is ImagePost -> inflateImageContent(post, inflater)
                is LinkPost -> inflateLinkContent(post, inflater)
            }
        )
    }
}