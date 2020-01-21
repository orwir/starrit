package orwir.videoplayer

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import kotlinx.android.synthetic.main.videoplayer.view.*
import kotlinx.android.synthetic.main.vp_controller.view.*
import kotlinx.coroutines.*

class VideoPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr),
    CoroutineScope by CoroutineScope(Dispatchers.Main),
    Player.EventListener {

    val cover: ImageView by lazy { vp_cover }
    internal lateinit var player: ExoPlayer
    internal lateinit var video: MediaSource

    private var initial: Boolean = true
    private var started: Boolean = false
    private var repeat: Boolean = true
    private var volume: Float = 0f
    private var progressJob: Job? = null

    private val hud: View by lazy { vp_controller }
    private val repeatOn: Drawable
    private val repeatOff: Drawable
    private val volumeOn: Drawable
    private val volumeOff: Drawable
    private val fullscreenOn: Drawable
    private val fullscreenOff: Drawable

    init {
        LayoutInflater.from(context).inflate(R.layout.videoplayer, this, true)
        repeatOn = ContextCompat.getDrawable(context, R.drawable.ic_repeat_on)!!
        repeatOff = ContextCompat.getDrawable(context, R.drawable.ic_repeat_off)!!
        volumeOn = ContextCompat.getDrawable(context, R.drawable.ic_volume_on)!!
        volumeOff = ContextCompat.getDrawable(context, R.drawable.ic_volume_off)!!
        fullscreenOn = ContextCompat.getDrawable(context, R.drawable.ic_fullscreen_on)!!
        fullscreenOff = ContextCompat.getDrawable(context, R.drawable.ic_fullscreen_off)!!
        initControllerListeners()
    }

    fun setVideo(uri: Uri) {
        video = uri.toMediaSource(context)
    }

    fun start(restored: Boolean = false) {
        progressJob?.cancel()
        VideoPlayerHolder.swap(this)
        vp_surface.player = player
        player.prepare(video, !restored, initial)
        if (!restored) player.playWhenReady = true
        setVolume(volume)
        setRepeat(repeat)
        vp_surface.setVisible(true)
        showHUD(show = !player.playWhenReady, state = player.playWhenReady)
        player.addListener(this)
        initial = false
        started = true
        progressJob = launch { trackProgress() }
    }

    fun play() {
        player.playWhenReady = !player.playWhenReady
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

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        // todo: check how well it works
        showHUD(show = !isPlaying, state = isPlaying)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initial = true
        started = false
        setRepeat(repeat)
        setVolume(volume)
        beforeStart()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancel()
        VideoPlayerHolder.releaseSelf(this)
    }

    internal fun release() {
        initial = true
        player.removeListener(this)
        stop()
    }

    private fun beforeStart() {
        vp_surface.setVisible(false)
        showHUD(show = true, state = null)
    }

    private suspend fun trackProgress() {
        hud.apply {
            while (isActive) {
                delay(500)
                if (player.duration != C.TIME_UNSET) {
                    vp_remained.text = (player.duration - player.currentPosition).toTimeFormat()
                }
            }
        }
    }

    private fun setRepeat(enabled: Boolean) {
        repeat = enabled
        hud.vp_repeat.setImageDrawable(if (repeat) repeatOn else repeatOff)
        if (this::player.isInitialized) {
            player.repeatMode = if (repeat) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_OFF
        }
    }

    private fun setVolume(level: Float) {
        volume = level
        if (this::player.isInitialized) {
            player.audioComponent?.volume = volume
        }
        hud.apply {
            val drawable = if (volume > 0) volumeOn else volumeOff
            vp_volume.setImageDrawable(drawable)
            vp_volume_level.setImageDrawable(drawable)
        }
    }

    private fun initControllerListeners() {
        hud.apply {
            vp_veil.setOnClickListener {
                if (initial && !started) {
                    start()
                } else {
                    showHUD(show = !isHudVisible(), state = started && player.playWhenReady)
                }
            }
            vp_play.setOnClickListener { if (!started) start() else play() }
            vp_pause.setOnClickListener { play() }
            vp_repeat.setOnClickListener { setRepeat(!repeat) }
            vp_volume.setOnClickListener { setVolume(if (volume > 0f) 0f else 1f) }
            // todo: content seeker
            vp_volume_level.setOnClickListener { /*todo: show/hide volume seeker */ }
            // todo: volume seeker
            vp_fullscreen.setOnClickListener { /*todo: change fullscreen mode */ }
        }
    }

    /**
     * @param show show / hide HUD
     * @param state before start: null, paused: false, playing: true
     */
    private fun showHUD(show: Boolean, state: Boolean?) {
        hud.apply {
            vp_play.setVisible(show && state != true)
            vp_pause.setVisible(show && state == true)
            vp_repeat.setVisible(show && state != true)
            vp_volume.setVisible(show && state == null)
            vp_playback_seeker.setVisible(show && state != null)
            vp_remained.setVisible(show && state != null)
            vp_volume_level.setVisible(show && state != null)
            vp_volume_level_seeker.setVisible(false)
            vp_fullscreen.setVisible(show)
        }
    }

    private fun View.setVisible(visible: Boolean) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun isHudVisible() = hud.vp_fullscreen.visibility == View.VISIBLE

}