package orwir.videoplayer

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import kotlinx.android.synthetic.main.videoplayer.view.*

class PlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var player: ExoPlayer
    private lateinit var video: MediaSource

    init {
        LayoutInflater.from(context).inflate(R.layout.videoplayer, this, true)

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
        PlayerHolder.releaseSelf(this)
    }

    fun setCover(drawable: Drawable?) {
        player_cover.setImageDrawable(drawable)
    }

    fun setVideo(url: String) {
        video = Uri.parse(url).toMediaSource(context)
    }

    fun play() {
        
    }

    fun pause() {

    }

    fun stop() {
        player.stop(true)
        player_view.visibility = View.GONE
        player_play.visibility = View.VISIBLE
    }

    internal fun release() {
        stop()
    }

}