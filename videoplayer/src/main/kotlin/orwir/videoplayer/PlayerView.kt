package orwir.videoplayer

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.videoplayer.view.*
import java.util.*

class PlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var player: ExoPlayer? = null
    var url: String? = null

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
                // getString(R.styleable.PlayerView_thumbnail)?.let { player_thumb.load(it) }
            } finally {
                recycle()
            }
        }

        player_root.setOnClickListener {
            val player = this.player
                ?: throw IllegalStateException("[player] not set!")
            val mediaSource = url?.let { buildMediaSource(it.toUri()) }
                ?: throw IllegalStateException("[url] not set!")

            player_root.isClickable = false
            player.stop()
            player_view.player = player
            player.prepare(mediaSource, true, true)
            player.playWhenReady = true
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val userAgent = BuildConfig.LIBRARY_PACKAGE_NAME
        val type = uri.lastPathSegment?.toLowerCase(Locale.ENGLISH) ?: ""
        val factory = DefaultHttpDataSourceFactory(userAgent)

        return when {
            type.contains("mp3") || type.contains("mp4") ->
                ProgressiveMediaSource.Factory(factory).createMediaSource(uri)
            type.contains("m3u8") ->
                HlsMediaSource.Factory(factory).createMediaSource(uri)
            else ->
                DashMediaSource.Factory(DefaultDashChunkSource.Factory(factory), factory)
                    .createMediaSource(uri)
        }
    }

}