package orwir.gazzit.feed.content

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.feed.databinding.ViewContentImageBinding
import orwir.gazzit.feed.databinding.ViewContentLinkBinding
import orwir.gazzit.feed.model.ImagePost
import orwir.gazzit.feed.model.LinkPost

internal fun inflateLinkContent(post: LinkPost, inflater: LayoutInflater): View =
    ViewContentLinkBinding
        .inflate(inflater)
        .also {
            it.post = post
            it.listener = View.OnClickListener {
                // todo: implement global navigator
            }
        }
        .root

internal fun inflateImageContent(post: ImagePost, inflater: LayoutInflater): View =
    ViewContentImageBinding
        .inflate(inflater)
        .also {
            it.post = post
        }
        .root