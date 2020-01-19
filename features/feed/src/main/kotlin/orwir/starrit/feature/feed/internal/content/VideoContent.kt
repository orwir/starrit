package orwir.starrit.feature.feed.internal.content

import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import orwir.starrit.feature.feed.databinding.ViewContentVideoBinding
import orwir.starrit.listing.feed.VideoPost

@Suppress("FunctionName")
internal fun PostContentBinder.VideoContent(post: VideoPost, parent: ViewGroup): View =
    ViewContentVideoBinding
        .inflate(inflater, parent, true)
        .apply {
            player.setVideo(post.video.toUri())
            post.loadImageData(player.cover)
        }
        .root