package orwir.starrit.feature.feed.internal.content

import android.view.View
import orwir.starrit.feature.feed.databinding.ViewContentVideoBinding
import orwir.starrit.listing.feed.VideoPost

// todo: improve video content layout

@Suppress("FunctionName")
internal fun PostContentBinder.VideoContent(post: VideoPost): View =
    ViewContentVideoBinding
        .inflate(inflater)
        .also {
            it.post = post
            it.playerView.player = player
        }
        .root