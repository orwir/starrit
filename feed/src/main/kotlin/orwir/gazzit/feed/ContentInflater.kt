package orwir.gazzit.feed

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.common.GlobalNavigation
import orwir.gazzit.common.extensions.activityScope
import orwir.gazzit.common.view.loadImage
import orwir.gazzit.feed.databinding.*
import orwir.gazzit.feed.model.*

internal class ContentInflater {
    fun inflate(post: Post, inflater: LayoutInflater): View = when (post) {
        is TextPost -> inflateTextContent(post, inflater)
        is GifPost -> inflateGifContent(post, inflater)
        is ImagePost -> inflateImageContent(post, inflater)
        is VideoPost -> inflateVideoContent(post, inflater)
        is LinkPost -> inflateLinkContent(post, inflater)
    }
}

private fun inflateLinkContent(post: LinkPost, inflater: LayoutInflater): View =
    ViewContentLinkBinding
        .inflate(inflater)
        .apply {
            this.post = post
            val navigation: GlobalNavigation by activityScope()
            listener = View.OnClickListener { navigation.openBrowser(post.link) }
        }
        .root

private fun inflateImageContent(post: ImagePost, inflater: LayoutInflater): View =
    ViewContentImageBinding
        .inflate(inflater)
        .also { it.post = post }
        .root

private fun inflateGifContent(post: GifPost, inflater: LayoutInflater): View =
    ViewContentGifBinding
        .inflate(inflater)
        .apply {
            this.post = post
            listener = View.OnClickListener {
                playing = if (playing == true) {
                    gif.loadImage(post.source, gif.drawable)
                    false
                } else {
                    gif.loadImage(post.gif, gif.drawable)
                    true
                }
            }
        }
        .root

private fun inflateTextContent(post: TextPost, inflater: LayoutInflater): View =
    ViewContentTextBinding
        .inflate(inflater)
        .also { it.post = post }
        .root

private fun inflateVideoContent(post: VideoPost, inflater: LayoutInflater): View =
    ViewContentVideoBinding
        .inflate(inflater)
        .also {
            // todo: implement video render
        }
        .root