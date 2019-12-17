package orwir.gazzit.feed.content

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.common.view.loadImage
import orwir.gazzit.feed.databinding.ViewContentGifBinding
import orwir.gazzit.model.Post

internal fun isGif(post: Post): Boolean = post.url.endsWith(".gif")

internal fun inflateGifContent(post: Post, inflater: LayoutInflater): View =
    ViewContentGifBinding
        .inflate(inflater)
        .apply {
            // todo: consider image settings and auto-play
            clickable = true
            playing = false
            url = post.imageUrl()

            listener = View.OnClickListener {
                playing = if (playing!!) {
                    content.loadImage(post.imageUrl(), content.drawable)
                    false
                } else {
                    content.loadImage(post.gif(), content.drawable)
                    true
                }
            }
        }
        .root

private fun Post.gif() = media?.oembed?.thumbnail ?: url