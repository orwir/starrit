package orwir.gazzit.listing.content

import android.view.LayoutInflater
import orwir.gazzit.databinding.ContentTextBinding
import orwir.gazzit.model.Post

fun Post.isText() = text?.isNotBlank() ?: false

fun inflateTextContent(post: Post, inflater: LayoutInflater) = ContentTextBinding
    .inflate(inflater)
    .apply { this.post = post }
    .root