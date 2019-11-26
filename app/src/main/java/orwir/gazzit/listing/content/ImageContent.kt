package orwir.gazzit.listing.content

import android.view.LayoutInflater
import coil.api.load
import orwir.gazzit.R
import orwir.gazzit.databinding.ContentImageBinding
import orwir.gazzit.model.Post

fun Post.isImage() = (hint == "image" || (hint == "link" && domain.startsWith("i.")))
        && !url.endsWith(".gifv") && !url.endsWith(".gif")

fun inflateImageContent(post: Post, inflater: LayoutInflater) = ContentImageBinding
    .inflate(inflater)
    .apply {
        image.load(post.previewThumb) {
            placeholder(R.drawable.ic_image_placeholder)
        }

        image.setOnClickListener {
            image.load(post.previewSource) {
                placeholder(image.drawable)
            }
            image.isEnabled = false
        }
    }
    .root