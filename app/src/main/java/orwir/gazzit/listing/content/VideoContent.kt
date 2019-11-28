package orwir.gazzit.listing.content

import android.view.LayoutInflater
import orwir.gazzit.databinding.ContentVideoBinding
import orwir.gazzit.model.Post

fun Post.isVideo() = false

fun inflateVideoContent(post: Post, inflater: LayoutInflater) = ContentVideoBinding
    .inflate(inflater)
    .apply {

    }
    .root