package orwir.gazzit.common.view

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.api.load
import org.koin.core.context.GlobalContext

@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(show: Boolean = false) {
    visibility = if (show) VISIBLE else GONE
}

@BindingAdapter("imageUrl", "placeholder", requireAll = false)
fun ImageView.loadImage(url: String, placeholder: Drawable? = null) {
    val loader: ImageLoader = GlobalContext.get().koin.get()
    loader.load(context, url) {
        placeholder?.let(::placeholder)
        target(this@loadImage)
    }
}