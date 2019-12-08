package orwir.gazzit.listing.content

import android.view.LayoutInflater
import coil.api.load
import com.google.android.exoplayer2.ExoPlayer
import org.koin.core.context.GlobalContext
import orwir.gazzit.databinding.ContentVideoBinding
import orwir.gazzit.model.Post

fun Post.isVideo() = url.endsWith(".gifv")

fun inflateVideoContent(post: Post, inflater: LayoutInflater) = ContentVideoBinding
    .inflate(inflater)
    .apply {
        thumb.load(post.previewThumb)
        media.player = GlobalContext.get().koin.get<ExoPlayer>()
        media.url = post.url.replace(".gifv", ".mp4")
    }
    .root