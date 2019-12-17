package orwir.gazzit.feed.content

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.feed.databinding.ViewContentLinkBinding
import orwir.gazzit.model.Post

internal fun inflateLinkContent(post: Post, inflater: LayoutInflater): View =
    ViewContentLinkBinding
        .inflate(inflater)
        .apply {
            // todo: consider image settings
            this.post = post
            this.listener = View.OnClickListener {
                TODO("not implemented yet")
            }
        }
        .root