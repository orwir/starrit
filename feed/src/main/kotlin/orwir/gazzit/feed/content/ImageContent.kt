package orwir.gazzit.feed.content

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.common.Settings
import orwir.gazzit.common.view.loadImage
import orwir.gazzit.feed.databinding.ViewContentImageBinding
import orwir.gazzit.model.LoadSourceImage
import orwir.gazzit.model.Post

internal fun isImage(post: Post): Boolean = isJustImage(post.url) || post.isImgur()

internal fun inflateImageContent(post: Post, inflater: LayoutInflater): View =
    ViewContentImageBinding
        .inflate(inflater)
        .apply {
            // todo: implement load by click over wifi if current network is not
            clickable = Settings.loadSourceImage == LoadSourceImage.ByClick
            listener = View.OnClickListener {
                image.loadImage(post.imageSource(), image.drawable)
                image.isEnabled = false
            }
            url = post.imageUrl()
        }
        .root

internal fun Post.imageUrl(): String = when (Settings.loadSourceImage) {
    LoadSourceImage.ByClick -> imagePreview()
    LoadSourceImage.OverWifi -> TODO("check network state")
    LoadSourceImage.Always -> imageSource()
}

private fun isJustImage(url: String) = arrayOf(".png", ".jpg").contains(url.takeLast(4))

private fun Post.isImgur() = domain == "imgur.com"

private fun Post.imagePreview(): String = preview
    ?.images
    ?.get(0)
    ?.resolutions
    ?.get(0)
    ?.url
    ?.replace("&amp;", "&")
    ?: if (isJustImage(thumbnail)) thumbnail else ""

private fun Post.imageSource(): String = preview
    ?.images
    ?.get(0)
    ?.source
    ?.url
    ?.replace("&amp;", "&")
    ?: if (isJustImage(url)) url else ""