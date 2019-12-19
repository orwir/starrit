package orwir.gazzit.common.view

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.api.load
import coil.request.RequestDisposable
import org.koin.core.context.GlobalContext

@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(show: Boolean = false) {
    visibility = if (show) VISIBLE else GONE
}

@BindingAdapter("image", "placeholder", requireAll = false)
fun ImageView.loadImage(image: String, placeholder: Drawable? = null) {
    if (tag is RequestDisposable) {
        (tag as RequestDisposable).dispose()
    }
    if (image.isBlank()) {
        setImageDrawable(placeholder)
    } else {
        val loader: ImageLoader = GlobalContext.get().koin.get()
        tag = loader.load(context, image) {
            crossfade(200)
            placeholder?.let(::placeholder)
            target(this@loadImage)
        }
    }
}