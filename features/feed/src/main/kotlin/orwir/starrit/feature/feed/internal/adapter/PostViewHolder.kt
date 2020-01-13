package orwir.starrit.feature.feed.internal.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.transform.CircleCropTransformation
import coil.transform.Transformation
import orwir.starrit.feature.feed.R
import orwir.starrit.feature.feed.databinding.ViewPostBinding
import orwir.starrit.feature.feed.internal.content.PostContentBinder
import orwir.starrit.listing.feed.Post

internal class PostViewHolder(
    private val binding: ViewPostBinding,
    private val contentBinder: PostContentBinder
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.viewModel = PostViewModel(post, ::clickHandler)
        binding.content.removeAllViews()
        contentBinder.inflate(post, binding.content)
    }

    private fun clickHandler(view: View, post: Post) {
        when (view.id) {
            R.id.share -> {
                val content = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.contentUrl)
                }
                itemView.context.startActivity(Intent.createChooser(content, post.title))
            }
        }
    }

}

internal class PostViewModel(post: Post, onClick: (View, Post) -> Unit) {

    val icon = post.subreddit.icon
    val iconTransformations = listOf<Transformation>(CircleCropTransformation())
    val subreddit = post.subreddit.name
    val author = post.author
    val created = post.created
    val nsfw = post.nsfw
    val spoiler = post.spoiler
    val title = post.title
    val domain = post.domain
    val showDomain = !post.selfDomain
    val score = post.score
    val comments = post.comments

    val voteUp = View.OnClickListener { onClick(it, post) }
    val voteDown = View.OnClickListener { onClick(it, post) }
    val openComments = View.OnClickListener { onClick(it, post) }
    val share = View.OnClickListener { onClick(it, post) }
    val more = View.OnClickListener { onClick(it, post) }

}