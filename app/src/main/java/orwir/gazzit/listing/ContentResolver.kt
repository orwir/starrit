package orwir.gazzit.listing

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import coil.api.load
import orwir.gazzit.R
import orwir.gazzit.databinding.ContentGifBinding
import orwir.gazzit.databinding.ContentImageBinding
import orwir.gazzit.databinding.ContentLinkBinding
import orwir.gazzit.databinding.ContentTextBinding
import orwir.gazzit.model.Post

fun Post.contentLayout(inflater: LayoutInflater): View = when {
    isText() -> textContent(this, inflater)
    isImage() -> imageContent(this, inflater)
    isGif() -> gifContent(this, inflater)
    else -> linkContent(this, inflater)
}


private fun Post.isText() = text?.isNotBlank() ?: false

private fun Post.isImage() = (hint == "image" || (hint == "link" && domain.startsWith("i.")))
        && !url.endsWith(".gifv") && !url.endsWith(".gif")

private fun Post.isGif() = hint == "image"
        || url.endsWith(".gif")
        || url.endsWith(".gifv")
        || domain == "gfycat.com"


private fun textContent(post: Post, inflater: LayoutInflater) = ContentTextBinding
    .inflate(inflater)
    .apply { this.post = post }
    .root

private fun linkContent(post: Post, inflater: LayoutInflater) = ContentLinkBinding
    .inflate(inflater)
    .apply {
        this.post = post
        link.setOnClickListener { openLink(inflater.context, Uri.parse(post.url)) }
    }
    .root

private fun imageContent(post: Post, inflater: LayoutInflater) = ContentImageBinding
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

private fun gifContent(post: Post, inflater: LayoutInflater) = ContentGifBinding
    .inflate(inflater)
    .apply {
        image.load(post.previewThumb) {
            placeholder(R.drawable.ic_image_placeholder)
        }
        layout.setOnClickListener {
            if (post.domain == "i.redd.it") {
                if (type.visibility == View.VISIBLE) {
                    type.visibility = View.GONE
                    image.load(post.url) {
                        placeholder(image.drawable)
                    }
                } else {
                    type.visibility = View.VISIBLE
                    image.load(post.previewThumb) {
                        placeholder(image.drawable)
                    }
                }
            } else {
                val uri = if (post.domain == "gfycat.com") {
                    with(Uri.parse(post.url)) {
                        Uri.Builder()
                            .scheme(scheme)
                            .authority(authority)
                            .path("ifr")
                            .appendPath(lastPathSegment)
                            .build()
                    }
                } else {
                    Uri.parse(post.url)
                }
                openLink(inflater.context, uri)
            }
        }
    }
    .root

private fun openLink(context: Context, uri: Uri) {
    CustomTabsIntent
        .Builder()
        .build()
        .launchUrl(context, uri)
}