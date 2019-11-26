package orwir.gazzit.listing

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import orwir.gazzit.R
import orwir.gazzit.common.squeeze
import orwir.gazzit.databinding.ViewPostBinding
import orwir.gazzit.listing.content.inflateContentLayout
import orwir.gazzit.model.Post

class ListingAdapter : PagedListAdapter<Post, ViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewPostBinding
        .inflate(LayoutInflater.from(parent.context), parent, false)
        .run(::ViewHolder)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
}

class ViewHolder(private val binding: ViewPostBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        val res = itemView.resources
        with(binding) {
            this.post = post
            created.text = DateUtils.getRelativeDateTimeString(
                itemView.context,
                post.created * 1000,
                DateUtils.HOUR_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_ALL
            )
            score.text = if (post.hideScore) {
                res.getString(R.string.score_hidden)
            } else {
                post.score.squeeze()
            }
            commentsCount.text = post.comments.squeeze()

            content.removeAllViews()
            content.addView(post.inflateContentLayout(LayoutInflater.from(itemView.context)))
        }
    }

}