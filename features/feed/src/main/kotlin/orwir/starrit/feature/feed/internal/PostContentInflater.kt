package orwir.starrit.feature.feed.internal

import android.view.LayoutInflater
import android.view.View
import coil.ImageLoader
import com.google.android.exoplayer2.ExoPlayer
import orwir.starrit.feature.feed.FeedNavigation
import orwir.starrit.feature.feed.databinding.*
import orwir.starrit.listing.feed.*
import orwir.starrit.view.extension.load

internal class PostContentInflater(
    private val navigation: FeedNavigation,
    private val player: ExoPlayer,
    private val imageLoader: ImageLoader
) {

    fun inflate(post: Post, inflater: LayoutInflater): View = when (post) {
        is TextPost -> inflateTextContent(post, inflater)
        is GifPost -> inflateGifContent(post, inflater)
        is ImagePost -> inflateImageContent(post, inflater)
        is VideoPost -> inflateVideoContent(post, inflater)
        is LinkPost -> inflateLinkContent(post, inflater)
    }

    private fun inflateLinkContent(post: LinkPost, inflater: LayoutInflater): View =
        ViewContentLinkBinding
            .inflate(inflater)
            .also {
                it.post = post
                it.listener = View.OnClickListener { navigation.openBrowser(post.link) }
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
                    gif.load(if (playing == true) post.imageSource else post.gif, imageLoader) {
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
            .also {
                it.post = post
                it.playerView.player = player
            }
            .root

}