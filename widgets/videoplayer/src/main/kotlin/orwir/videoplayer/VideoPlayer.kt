package orwir.videoplayer

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import kotlinx.android.synthetic.main.videoplayer.view.*
import kotlinx.coroutines.*

class VideoPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr),
    Player.EventListener,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    val cover: ImageView by lazy { vp_cover }
    private lateinit var player: ExoPlayer
    private lateinit var video: MediaSource
    private var progressJob: Job? = null
    private var initial = true
    private var started = false
    private var repeat = false
    private val repeatOn: Drawable by lazy { ContextCompat.getDrawable(context, R.drawable.ic_repeat_on)!! }
    private val repeatOff: Drawable by lazy { ContextCompat.getDrawable(context, R.drawable.ic_repeat_off)!! }

    init {
        LayoutInflater.from(context).inflate(R.layout.videoplayer, this, true)
        beforeStart()
        setRepeat(true)
        vp_veil.setOnClickListener {
            if (initial && !started) {
                start()
            } else {
                showHUD(show = vp_fullscreen.visibility != View.VISIBLE, state = started && player.playWhenReady)
            }
        }
        vp_play.setOnClickListener { if (!started) start() else play() }
        vp_pause.setOnClickListener { play() }
        vp_repeat.setOnClickListener { setRepeat(!repeat) }
        vp_volume.setOnClickListener { }
        // todo: content seeker
        vp_volume_level.setOnClickListener { /*todo: show/hide volume seeker */ }
        // todo: volume seeker
        vp_fullscreen.setOnClickListener { }
    }

    fun setVideo(uri: Uri) {
        video = uri.toMediaSource(context)
    }

    fun start(restored: Boolean = false) {
        progressJob?.cancel()
        VideoPlayerHolder.swap(this)
        vp_surface.player = player
        vp_surface.visible(true)
        player.prepare(video, !restored, initial)
        if (!restored) {
            player.audioComponent?.volume = 0F // todo: get from configuration
            player.playWhenReady = true
        }
        setRepeat(repeat)
        showHUD(show = !player.playWhenReady, state = player.playWhenReady)
        player.addListener(this)
        initial = false
        started = true
        progressJob = launch { trackProgress() }
    }

    fun play() {
        player.playWhenReady = !player.playWhenReady
        showHUD(show = !player.playWhenReady, state = false)
    }

    fun stop() {
        player.stop(true)
        progressJob?.cancel()
        started = false
        beforeStart()
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
        cancel()
        VideoPlayerHolder.releaseSelf(this)
    }

    internal fun setPlayer(player: ExoPlayer) {
        this.player = player
    }

    internal fun release() {
        initial = true
        player.removeListener(this)
        stop()
    }

    private fun beforeStart() {
        vp_surface.visible(false)
        showHUD(show = true, state = null)
    }

    private suspend fun trackProgress() {
        while (isActive) {
            delay(500)
            if (player.duration != C.TIME_UNSET) {
                vp_remained.text = asRemainedTime(player.duration, player.currentPosition)
            }
        }
    }

    /**
     * @param state before start: null, paused: false, playing: true
     */
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

    private fun setRepeat(enabled: Boolean) {
        repeat = enabled
        vp_repeat.setImageDrawable(if (repeat) repeatOn else repeatOff)
        if (this::player.isInitialized) {
            player.repeatMode = if (repeat) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_OFF
        }
    }

    private fun View.visible(value: Boolean) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

}