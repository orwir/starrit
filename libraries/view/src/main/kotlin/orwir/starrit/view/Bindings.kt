package orwir.starrit.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.transform.*

@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(show: Boolean = false) {
    visibility = if (show) VISIBLE else GONE
}

@BindingAdapter("source", "preview", "placeholder", "error", "transformations", requireAll = false)
fun ImageView.load(
    source: String,
    preview: String? = null,
    placeholder: Drawable? = null,
    error: Drawable? = null,
    transformations: List<TransformationType>? = null
) {
    setImageDrawable(placeholder)
    load(preview?.takeIf { it.isNotBlank() } ?: source) {
        transformations?.let { TransformationType.toCoil(context, it) }?.let(::transformations)
        crossfade(400)
        placeholder(placeholder)
        error(error)
        target {
            setImageDrawable(it)
            if (preview?.isNotBlank() == true) {
                load(source, null, it, error, transformations)
            }
        }
    }
}

enum class TransformationType {
    CIRCLE,
    BLUR,
    GRAY,
    ROUNDED;

    companion object {
        fun toCoil(context: Context, list: List<TransformationType>): List<Transformation>? {
            val result = mutableListOf<Transformation>()
            if (list.contains(CIRCLE)) result.add(CircleCropTransformation())
            if (list.contains(BLUR)) result.add(BlurTransformation(context)) // todo: parameters?
            if (list.contains(GRAY)) result.add(GrayscaleTransformation())
            if (list.contains(ROUNDED)) result.add(RoundedCornersTransformation()) // todo: radius
            return result.takeIf { it.isNotEmpty() }
        }
    }
}