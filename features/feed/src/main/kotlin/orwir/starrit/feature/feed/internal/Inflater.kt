package orwir.starrit.feature.feed.internal

import android.view.LayoutInflater
import android.view.View
import orwir.starrit.core.di.DI
import orwir.starrit.feature.feed.FeedNavigation
import orwir.starrit.feature.feed.databinding.*
import orwir.starrit.listing.feed.*
import orwir.starrit.view.load

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
            val navigation: FeedNavigation = DI.dependency()
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
                gif.load(if (playing == true) post.source else post.gif) {
                    placeholder(gif.drawable)
                }
                playing = playing?.let { !it } ?: true
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
        .apply {
            this.post = post
            playerView.player = DI.dependency()
        }
        .root