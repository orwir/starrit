package orwir.starrit.feature.feed.internal.content

import android.view.View
import android.view.ViewGroup
import orwir.starrit.feature.feed.databinding.ViewContentVideoBinding
import orwir.starrit.listing.feed.VideoPost

// todo: improve video content layout

@Suppress("FunctionName")
internal fun PostContentBinder.VideoContent(post: VideoPost, parent: ViewGroup): View =
    ViewContentVideoBinding
        .inflate(inflater, parent, true)
        .also {
            it.post = post
            it.playerView.player = player
        }
        .root