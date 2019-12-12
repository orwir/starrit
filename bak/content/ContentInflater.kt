package orwir.gazzit.listing.content

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.model.Post

fun Post.inflateContentLayout(inflater: LayoutInflater): View = when {
    isText() -> inflateTextContent(this, inflater)
    isImage() -> inflateImageContent(this, inflater)
    isGif() -> inflateGifContent(this, inflater)
    isVideo() -> inflateVideoContent(this, inflater)
    else -> inflateLinkContent(this, inflater)
}