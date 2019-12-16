package orwir.gazzit.feed.content

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.feed.databinding.ViewContentVideoBinding
import orwir.gazzit.model.Post

fun isVideo(post: Post): Boolean = TODO("not implemented yet")

fun inflateVideoContent(post: Post, inflater: LayoutInflater): View =
    ViewContentVideoBinding
        .inflate(inflater)
        .apply {

        }
        .root