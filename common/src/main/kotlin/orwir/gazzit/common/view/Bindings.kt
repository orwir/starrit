package orwir.gazzit.common.view

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

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String) {
    val loader: ImageLoader = GlobalContext.get().koin.get()
    loader.load(context, url) {
        target(this@loadImage)
    }
}