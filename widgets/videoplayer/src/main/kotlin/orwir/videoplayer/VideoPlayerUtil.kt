package orwir.videoplayer

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.util.*
import java.util.concurrent.TimeUnit

internal fun Uri.toMediaSource(context: Context): MediaSource {
    val type = lastPathSegment?.toLowerCase(Locale.ENGLISH) ?: ""
    val userAgent = Util.getUserAgent(context, context.applicationContext.packageName)
    val factory = DefaultDataSourceFactory(context, userAgent)

    return when {
        type.contains("mp3") || type.contains("mp4") ->
            ProgressiveMediaSource.Factory(factory).createMediaSource(this)
        type.contains("m3u8") ->
            HlsMediaSource.Factory(factory).createMediaSource(this)
        else ->
            DashMediaSource.Factory(DefaultDashChunkSource.Factory(factory), factory).createMediaSource(this)
    }
}

internal fun Long.toTimeFormat(): String {
    fun Long.toBit() = this.takeIf { it > 0 }?.let { ":%02d".format(it) } ?: ""
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this)
    return "${hours.toBit()}${minutes.toBit()}${seconds.toBit()}"
}