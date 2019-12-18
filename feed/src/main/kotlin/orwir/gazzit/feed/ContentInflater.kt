package orwir.gazzit.feed

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.feed.databinding.ViewContentImageBinding
import orwir.gazzit.feed.databinding.ViewContentLinkBinding
import orwir.gazzit.feed.model.ImagePost
import orwir.gazzit.feed.model.LinkPost
import orwir.gazzit.feed.model.Post

internal class ContentInflater {

    fun inflate(post: Post, inflater: LayoutInflater): View = when (post) {
        is ImagePost -> inflateImageContent(post, inflater)
        is LinkPost -> inflateLinkContent(post, inflater)
    }
}

private fun inflateLinkContent(post: LinkPost, inflater: LayoutInflater): View =
    ViewContentLinkBinding
        .inflate(inflater)
        .also {
            it.post = post
            it.listener = View.OnClickListener {
                // todo: implement global navigator
            }
        }
        .root

private fun inflateImageContent(post: ImagePost, inflater: LayoutInflater): View =
    ViewContentImageBinding
        .inflate(inflater)
        .also {
            it.post = post
        }
        .root