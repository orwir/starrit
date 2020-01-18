package orwir.videoplayer

import android.content.Context
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

class VideoPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), Player.EventListener {

    val cover: ImageView by lazy { vp_cover }
    lateinit var player: ExoPlayer
    private lateinit var video: MediaSource
    private var initial = true
    private var started = false

    init {
        LayoutInflater.from(context).inflate(R.layout.videoplayer, this, true)
        beforeStart()
        vp_veil.setOnClickListener {
            if (initial && !started) {
                start()
            } else {
                showHUD(show = vp_fullscreen.visibility != View.VISIBLE, state = started && player.playWhenReady)
            }
        }
        vp_play.setOnClickListener { if (!started) start() else playPause() }
        vp_pause.setOnClickListener { playPause() }
        vp_repeat.setOnClickListener { }
        vp_volume.setOnClickListener { }
        // todo: content seeker
        vp_volume_level.setOnClickListener { /*todo: show/hide volume seeker */ }
        // todo: volume seeker
        vp_fullscreen.setOnClickListener { }
    }

    fun setVideo(uri: Uri) {
        video = uri.toMediaSource(context)
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_ENDED) {
            showHUD(show = true, state = false)
            started = false
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initial = true
        started = false
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        VideoPlayerHolder.releaseSelf(this)
    }

    internal fun release() {
        player.removeListener(this)
        stop()
    }

    private fun beforeStart() {
        vp_surface.visible(false)
        showHUD(show = true, state = null)
    }

    private fun start() {
        VideoPlayerHolder.swap(this)
        vp_surface.player = player
        vp_surface.visible(true)
        showHUD(show = false, state = true)
        player.prepare(video, true, true)
        player.audioComponent?.volume = 0F // todo: get from configuration
        player.playWhenReady = true
        player.addListener(this)
        initial = false
        started = true
    }

    private fun playPause() {
        player.playWhenReady = !player.playWhenReady
        showHUD(show = !player.playWhenReady, state = false)
    }

    private fun stop() {
        player.stop(true)
        started = false
        beforeStart()
    }

    private fun showHUD(show: Boolean, state: Boolean?) {
        vp_play.visible(show && state != true)
        vp_pause.visible(show && state == true)
        vp_repeat.visible(show && state != true)
        vp_volume.visible(show && state == null)
        vp_playback_seeker.visible(show && state != null)
        vp_remained.visible(show && state != null)
        vp_volume_level.visible(show && state != null)
        vp_volume_level_seeker.visible(false)
        vp_fullscreen.visible(show)
    }

    private fun View.visible(value: Boolean) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

}