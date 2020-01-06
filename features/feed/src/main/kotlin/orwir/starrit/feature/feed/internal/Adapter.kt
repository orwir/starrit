package orwir.starrit.feature.feed.internal

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.feature.feed.databinding.ViewPostBinding
import orwir.starrit.listing.feed.Post

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
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
}

internal class ViewHolder(
    private val binding: ViewPostBinding
) : RecyclerView.ViewHolder(binding.root), KoinComponent {

    private val inflater: LayoutInflater = LayoutInflater.from(itemView.context)
    private val content: ContentInflater by inject()

    fun bind(post: Post) {
        binding.post = post
        // BUG: enable/disable doesn't work on xml even with "clickable = false"
        binding.nsfw.isEnabled = post.nsfw
        binding.spoiler.isEnabled = post.spoiler
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
        val chooser = Intent.createChooser(content, post.title)
        itemView.context.startActivity(chooser)
    }

}