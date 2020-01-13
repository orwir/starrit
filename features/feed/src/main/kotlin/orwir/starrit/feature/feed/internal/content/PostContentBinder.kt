package orwir.starrit.feature.feed.internal.content

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.exoplayer2.ExoPlayer
import orwir.starrit.feature.feed.FeedNavigation
import orwir.starrit.feature.feed.R
import orwir.starrit.listing.feed.*
import orwir.starrit.view.extension.getColorFromAttr

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

    fun Context.createImagePlaceholder(image: ImageData): Drawable {
        val color = getColorFromAttr(R.attr.colorGhost)
        val placeholder = ColorDrawable(color)
        placeholder.setBounds(0, 0, image.width, image.height)
        return placeholder
    }

}