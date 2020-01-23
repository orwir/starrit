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
import androidx.navigation.findNavController
import orwir.starrit.core.extension.observe
import orwir.starrit.feature.feed.FeedFragmentDirections
import orwir.starrit.feature.feed.FeedNavigation
import orwir.starrit.feature.feed.FeedPreferences
import orwir.starrit.feature.feed.R
import orwir.starrit.listing.feed.*
import orwir.starrit.listing.model.Image
import orwir.starrit.view.binding.ImageViewBinding.load
import orwir.starrit.view.binding.setVisibleOrGone
import orwir.starrit.view.extension.getThemeColor

internal class PostContentBinder(
    val navigation: FeedNavigation,
    val inflater: LayoutInflater,
    val preferences: FeedPreferences,
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

    fun ImageView.setupFullscreenButton(post: Post) {
        setOnClickListener {
            val direction = FeedFragmentDirections.toContentFragment(post)
            findNavController().navigate(direction)
        }
    }

    fun ImageView.loadImageData(post: Post, progress: ProgressBar? = null) {
        val shouldBlur = post.spoiler || (post.nsfw && isBlurNsfw())
        val source = post.imageSource.url.takeUnless { shouldBlur } ?: post.imageBlurred.url
        val preview = post.imagePreview.url.takeUnless { shouldBlur }
        val placeholder = context.createImagePlaceholder(post.imagePreview)

        loadImageDataInternal(source, preview, placeholder, progress)
        if (shouldBlur) {
            setOnClickListener {
                setOnClickListener(null)
                loadImageDataInternal(
                    source = post.imageSource.url,
                    preview = null,
                    placeholder = drawable,
                    progress = progress
                )
            }
        }
    }

    private fun Context.createImagePlaceholder(image: Image): Drawable {
        val color = getThemeColor(R.attr.colorGhost)
        val placeholder = ColorDrawable(color)
        placeholder.setBounds(0, 0, image.width, image.height)
        return placeholder
    }

    private fun ImageView.loadImageDataInternal(
        source: String,
        preview: String?,
        placeholder: Drawable,
        progress: ProgressBar?
    ) {
        owner.observe(load(source, preview, placeholder, placeholder)) {
            progress?.setVisibleOrGone(it)
        }
    }

    private fun isBlurNsfw() = preferences.blurNsfw

}