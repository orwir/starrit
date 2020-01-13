package orwir.starrit.feature.feed.internal.content

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
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

    fun inflate(post: Post): View = when (post) {
        is TextPost -> TextContent(post)
        is GifPost -> GifContent(post)
        is ImagePost -> ImageContent(post)
        is VideoPost -> VideoContent(post)
        is LinkPost -> LinkContent(post)
    }

    fun Context.createImagePlaceholder(image: ImageData): Drawable {
        val color = getColorFromAttr(R.attr.colorGhost)
        val placeholder = ColorDrawable(color)
        placeholder.setBounds(0, 0, image.width, image.height)
        return placeholder
    }

}