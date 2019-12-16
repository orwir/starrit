package orwir.gazzit.feed.content

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.feed.databinding.ViewContentImageBinding
import orwir.gazzit.model.Post

internal fun isImage(post: Post): Boolean = TODO("not implemented yet")

internal fun inflateImageContent(post: Post, inflater: LayoutInflater): View =
    ViewContentImageBinding
        .inflate(inflater)
        .apply {

        }
        .root