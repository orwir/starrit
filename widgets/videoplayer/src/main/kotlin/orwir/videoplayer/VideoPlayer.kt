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
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import kotlinx.android.synthetic.main.videoplayer.view.*
import kotlinx.android.synthetic.main.videoplayer_controls.view.*

class VideoPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var player: ExoPlayer
    val cover: ImageView by lazy { vp_cover }

    private lateinit var video: MediaSource
    private val listener = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {
                Player.STATE_BUFFERING -> {
                }
                Player.STATE_READY -> {
                }
                Player.STATE_IDLE -> {
                }
                Player.STATE_ENDED -> stop()
            }
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.videoplayer, this, true)
        vp_cover.setOnClickListener { start() }
        exo_stop.setOnClickListener { stop() }
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

    fun start() {
        VideoPlayerHolder.swap(this)
        vp_content.player = player
        player.addListener(listener)
        player.prepare(video, true, true)
        player.audioComponent?.volume = 0F
        vp_content.visibility = View.VISIBLE
        vp_content.hideController()
    }

    fun stop() {
        player.removeListener(listener)
        player.stop(true)
        vp_content.visibility = View.GONE
    }

    internal fun release() {
        stop()
    }

}