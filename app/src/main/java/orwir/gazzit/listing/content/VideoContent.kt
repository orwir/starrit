package orwir.gazzit.listing.content

import android.view.LayoutInflater
import coil.Coil
import coil.api.load
import com.google.android.exoplayer2.ExoPlayer
import org.koin.core.context.GlobalContext
import orwir.gazzit.databinding.ContentVideoBinding
import orwir.gazzit.model.Post

fun Post.isVideo() = false //url.endsWith(".gifv")

fun inflateVideoContent(post: Post, inflater: LayoutInflater) = ContentVideoBinding
    .inflate(inflater)
    .apply {
//        Coil.load(inflater.context, post.previewThumb) {
//            target {
//                playerView.useArtwork = true
//                playerView.defaultArtwork = it
//            }
//        }
//
//        image.load(post.previewThumb) {
//            placeholder(R.drawable.ic_image_placeholder)
//        }
//
        val koin = GlobalContext.get().koin
//        val player: ExoPlayer = koin.get()

//        val factory: DataSource.Factory = koin.get()
//
//        val uri = post.url.replace(".gifv", ".mp4").toUri()
//        val source = ProgressiveMediaSource.Factory(factory).createMediaSource(uri)
//
//        layout.setOnClickListener {
//            //type.visibility = View.GONE
//            if (image.visibility == View.VISIBLE) {
//                image.visibility = View.INVISIBLE
//
//                playerView.player = player
//
//                player.prepare(source)
//                player.playWhenReady = true
//                player.repeatMode = Player.REPEAT_MODE_ALL
//            } else {
//                image.visibility = View.VISIBLE
//            }
//        }
    }
    .root