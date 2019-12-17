package orwir.gazzit.feed.content

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.feed.databinding.ViewContentGifBinding
import orwir.gazzit.model.Post

internal fun isGif(post: Post): Boolean = false

internal fun inflateGifContent(post: Post, inflater: LayoutInflater): View =
    ViewContentGifBinding
        .inflate(inflater)
        .apply {

        }
        .root