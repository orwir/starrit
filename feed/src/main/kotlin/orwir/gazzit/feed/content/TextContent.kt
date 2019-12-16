package orwir.gazzit.feed.content

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.feed.databinding.ViewContentTextBinding
import orwir.gazzit.model.Post

internal fun isText(post: Post): Boolean = TODO("not implemented yet")

internal fun inflateTextContent(post: Post, inflater: LayoutInflater): View =
    ViewContentTextBinding
        .inflate(inflater)
        .apply {

        }
        .root