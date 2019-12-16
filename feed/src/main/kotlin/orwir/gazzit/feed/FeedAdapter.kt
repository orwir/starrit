package orwir.gazzit.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import orwir.gazzit.common.extensions.squeeze
import orwir.gazzit.feed.content.*
import orwir.gazzit.feed.databinding.ViewPostBinding
import orwir.gazzit.model.Post

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
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        val res = itemView.resources
        with(binding) {
            this.post = post
            created.text = android.text.format.DateUtils.getRelativeDateTimeString(
                itemView.context,
                post.created * 1000,
                android.text.format.DateUtils.HOUR_IN_MILLIS,
                android.text.format.DateUtils.WEEK_IN_MILLIS,
                android.text.format.DateUtils.FORMAT_ABBREV_ALL
            )
            score.text = if (post.hideScore) {
                res.getString(R.string.score_hidden)
            } else {
                post.score.squeeze()
            }
            commentsCount.text = post.comments.squeeze()

            content.removeAllViews()
            content.addView(inflateContentLayout(post, LayoutInflater.from(itemView.context)))
        }
    }

    private fun inflateContentLayout(post: Post, inflater: LayoutInflater): View = when {
        isText(post) -> inflateTextContent(post, inflater)
        isImage(post) -> inflateImageContent(post, inflater)
        isGif(post) -> inflateGifContent(post, inflater)
        isVideo(post) -> inflateVideoContent(post, inflater)
        else -> inflateLinkContent(post, inflater)
    }

}