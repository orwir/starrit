package orwir.gazzit.listing

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_post.view.*
import orwir.gazzit.R
import orwir.gazzit.common.squeezeFormat

class ListingAdapter : PagedListAdapter<Post, ViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_post, parent, false)
            .run(::ViewHolder)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val icon = view.subreddit_icon
    private val subreddit = view.subreddit_title
    private val author = view.author
    private val created = view.created
    private val title = view.title
    private val score = view.score
    private val comments = view.comments_count

    fun bind(post: Post) {
        subreddit.text = itemView.resources.getString(R.string.subbreddit, post.subreddit)
        author.text = itemView.resources.getString(R.string.author, post.author)
        created.text = DateUtils.getRelativeDateTimeString(
            itemView.context,
            post.created * 1000,
            DateUtils.HOUR_IN_MILLIS,
            DateUtils.WEEK_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_ALL
        )
        title.text = post.title
        score.text = post.score.squeezeFormat()
        comments.text = post.comments.squeezeFormat()
    }
}