package orwir.gazzit.feed.content

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.feed.databinding.ViewContentImageBinding
import orwir.gazzit.model.Post

internal fun isImage(post: Post): Boolean =
    with(post) {
        (hint == "image" || (post.hint == "link" && domain.startsWith("i.")))
                && !url.endsWith(".gifv") && !url.endsWith(".gif")
    }

internal fun inflateImageContent(post: Post, inflater: LayoutInflater): View =
    ViewContentImageBinding
        .inflate(inflater)
        .apply {
            //            image.load(post.previewThumb) {
//                placeholder(R.drawable.ic_image_placeholder)
//            }
//
//            image.setOnClickListener {
//                image.load(post.previewSource) {
//                    placeholder(image.drawable)
//                }
//                image.isEnabled = false
//            }
            // todo: consider image settings
            this.post = post
        }
        .root

/*
    val previewThumb = preview
        ?.images
        ?.get(0)
        ?.resolutions
        ?.get(0)
        ?.url
        ?.replace("&amp;", "&")
        ?: thumbnail

    val previewSource = preview
        ?.images
        ?.get(0)
        ?.source
        ?.url
        ?.replace("&amp;", "&")
        ?: url
 */