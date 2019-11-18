package orwir.gazzit.listing

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_post.view.*
import orwir.gazzit.R
import orwir.gazzit.common.squeeze
import orwir.gazzit.model.Post
import orwir.gazzit.model.PostType

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
    private val domain = view.domain
    private val nsfw = view.nsfw
    private val score = view.score
    private val comments = view.comments_count
    private val content = view.content

    fun bind(post: Post) {
        val res = itemView.resources

        subreddit.text = res.getString(R.string.subreddit, post.subreddit)
        author.text = res.getString(R.string.author, post.author)
        created.text = DateUtils.getRelativeDateTimeString(
            itemView.context,
            post.created * 1000,
            DateUtils.HOUR_IN_MILLIS,
            DateUtils.WEEK_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_ALL
        )
        title.text = post.title
        domain.text = post.domain
        nsfw.visibility = if (post.nsfw) View.VISIBLE else View.GONE
        score.text = if (post.hideScore) {
            res.getString(R.string.score_hidden)
        } else {
            post.score.squeeze()
        }
        comments.text = post.comments.squeeze()

        content.removeAllViews()
        post.content(itemView as ViewGroup)?.also { (type, view) ->
            when (type) {
                PostType.Text -> (view as TextView).text = post.text
            }
            content.addView(view)
        }
    }

    private fun Post.content(viewGroup: ViewGroup): Pair<PostType, View>? {
        val type = when {
            text?.isNotBlank() ?: false -> PostType.Text
            else -> return null
        }
        val layout = when (type) {
            PostType.Text -> R.layout.content_text
            else -> return null
        }.let { LayoutInflater.from(viewGroup.context).inflate(it, viewGroup, false) }

        return type to layout
    }
}