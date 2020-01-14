package orwir.videoplayer

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import kotlinx.android.synthetic.main.videoplayer.view.*

class VideoPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var player: ExoPlayer? = null
    val cover: ImageView by lazy { vp_cover }
    private var video: MediaSource? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.videoplayer, this, true)
        stop()

//        player_root.setOnClickListener {
//            PlayerHolder.swap(this)
//            player_view.visibility = View.VISIBLE // todo: move UI changes to separate method
//            player_play.visibility = View.GONE
//            player_view.player = player
//            player_view.controllerAutoShow = false
//            player.prepare(source, true, true)
//            player.playWhenReady = true
//        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        VideoPlayerHolder.releaseSelf(this)
    }

    fun setVideo(uri: Uri) {
        video = uri.toMediaSource(context)
    }

    fun setCover(drawable: Drawable?) {
        vp_cover.setImageDrawable(drawable)
    }

    fun play() {

    }

    fun pause() {

    }

    fun stop() {
        player?.stop(true)
        vp_play.visibility = View.VISIBLE
        vp_pause.visibility = View.GONE
        vp_stop.visibility = View.GONE
    }

    internal fun release() {
        stop()
    }

}