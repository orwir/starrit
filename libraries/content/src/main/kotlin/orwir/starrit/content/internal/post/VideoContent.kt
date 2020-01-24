package orwir.starrit.content.internal.post

import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import orwir.starrit.content.databinding.ViewContentVideoBinding
import orwir.starrit.content.post.VideoPost

@Suppress("FunctionName")
internal fun PostContentBinder.VideoContent(post: VideoPost, parent: ViewGroup): View =
    ViewContentVideoBinding
        .inflate(inflater, parent, true)
        .apply {
            player.setVideo(post.video.toUri())
            player.cover.loadImageData(post)
        }
        .root