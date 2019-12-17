package orwir.gazzit.feed.content

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.feed.databinding.ViewContentGifBinding
import orwir.gazzit.model.Post

internal fun isGif(post: Post): Boolean = post.url.endsWith(".gif")

internal fun inflateGifContent(post: Post, inflater: LayoutInflater): View =
    ViewContentGifBinding
        .inflate(inflater)
        .apply {
            // todo: consider image settings and auto-play
            url = post.url
            /*
            image.load(post.previewThumb) {
                placeholder(R.drawable.ic_image_placeholder)
            }
            layout.setOnClickListener {
                if (type.visibility == View.VISIBLE) {
                    type.visibility = View.GONE
                    image.load(post.media?.oembed?.thumbnail ?: post.url) {
                        placeholder(image.drawable)
                    }
                } else {
                    type.visibility = View.VISIBLE
                    image.load(post.previewThumb) {
                        placeholder(image.drawable)
                    }
                }
            }
             */
        }
        .root