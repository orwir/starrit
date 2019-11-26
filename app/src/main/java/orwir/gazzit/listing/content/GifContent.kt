package orwir.gazzit.listing.content

import android.view.LayoutInflater
import android.view.View
import coil.api.load
import orwir.gazzit.R
import orwir.gazzit.databinding.ContentGifBinding
import orwir.gazzit.model.Post

fun Post.isGif() =
    media?.oembed?.thumbnail?.endsWith(".gif") == true || url.endsWith(".gif")

fun inflateGifContent(post: Post, inflater: LayoutInflater) = ContentGifBinding
    .inflate(inflater)
    .apply {
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
    }
    .root