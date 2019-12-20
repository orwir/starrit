package orwir.videoplayer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import coil.api.load
import coil.request.RequestDisposable
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import kotlinx.android.synthetic.main.videoplayer.view.*

class PlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var player: ExoPlayer
    var coverUrl: String = ""
        set(value) {
            field = value
            loadCover(value)
        }
    var videoUrl: String = ""
        set(value) {
            field = value
            createMediaSource(value)
        }
    private lateinit var source: MediaSource

    init {
        LayoutInflater
            .from(context)
            .inflate(R.layout.videoplayer, this, true)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PlayerView,
            defStyleAttr,
            defStyleRes
        ).apply {
            try {
                getString(R.styleable.PlayerView_coverUrl)?.let { coverUrl = it }
                getString(R.styleable.PlayerView_videoUrl)?.let { videoUrl = it }
            } finally {
                recycle()
            }
        }
        player_root.setOnClickListener {
            PlayerHolder.swap(this)
            player_view.visibility = View.VISIBLE // todo: move UI changes to separate method
            player_play.visibility = View.GONE
            player_view.player = player
            player_view.controllerAutoShow = false
            player.prepare(source, true, true)
            player.playWhenReady = true
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        PlayerHolder.releaseSelf(this)
    }

    internal fun release() {
        player.stop(true)
        player_view.visibility = View.GONE
        player_play.visibility = View.VISIBLE
    }

    private fun loadCover(url: String) {
        if (player_cover.tag is RequestDisposable) {
            (player_cover.tag as RequestDisposable).dispose()
        }
        if (url.isBlank()) {
            player_cover.setImageDrawable(null)
        } else {
            player_cover.tag = player_cover.load(url)
        }
    }

    private fun createMediaSource(url: String) {
        if (url.isNotBlank()) {
            source = url.toUri().toMediaSource(context)
        }
    }

}