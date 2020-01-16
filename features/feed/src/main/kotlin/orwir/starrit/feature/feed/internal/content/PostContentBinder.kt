package orwir.starrit.feature.feed.internal.content

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.ExoPlayer
import orwir.starrit.feature.feed.FeedNavigation
import orwir.starrit.feature.feed.R
import orwir.starrit.listing.feed.*
import orwir.starrit.view.binding.ImageViewBinding.load
import orwir.starrit.view.binding.setVisibleOrGone
import orwir.starrit.view.extension.getThemeColor

internal class PostContentBinder(
    val navigation: FeedNavigation,
    val inflater: LayoutInflater,
    val player: ExoPlayer,
    private val ownerLiveData: LiveData<LifecycleOwner>
) {

    val owner: LifecycleOwner
        get() = ownerLiveData.value ?: throw IllegalStateException("Lifecycle owner not set!")

    fun inflate(post: Post, parent: ViewGroup) {
        when (post) {
            is TextPost -> TextContent(post, parent)
            is GifPost -> GifContent(post, parent)
            is ImagePost -> ImageContent(post, parent)
            is VideoPost -> VideoContent(post, parent)
            is LinkPost -> LinkContent(post, parent)
        }
    }

    fun ImageData.loadImageData(image: ImageView, progress: ProgressBar? = null) {
        val placeholder = image.context.createImagePlaceholder(this)
        image.load(source, preview, placeholder).observe(owner, Observer {
            progress?.setVisibleOrGone(it)
        })
    }

    private fun Context.createImagePlaceholder(image: ImageData): Drawable {
        val color = getThemeColor(R.attr.colorGhost)
        val placeholder = ColorDrawable(color)
        placeholder.setBounds(0, 0, image.width, image.height)
        return placeholder
    }

}