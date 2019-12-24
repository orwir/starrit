package orwir.starrit.view

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.api.load
import coil.request.RequestDisposable

@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(show: Boolean = false) {
    visibility = if (show) VISIBLE else GONE
}

@BindingAdapter("source", "preview", "placeholder", requireAll = false)
fun ImageView.load(
    source: String,
    preview: String? = null,
    placeholder: Drawable? = null
) {
    if (tag is RequestDisposable) {
        (tag as RequestDisposable).dispose()
    }
    val url1: String? = preview?.takeIf { it.isNotBlank() } ?: source.takeIf { it.isNotBlank() }
    val url2: String? = source.takeIf { url1 == preview && it.isNotBlank() }
    val loader = get<ImageLoader>()
    setImageDrawable(placeholder)
    tag = url1?.let { url ->
        loader.load(context, url) {
            crossfade(400)
            target { drawable ->
                this@load.setImageDrawable(drawable)
                tag = url2?.let { url ->
                    loader.load(context, url) {
                        crossfade(true)
                        placeholder(drawable)
                        target(this@load)
                    }
                }
            }
        }
    }
}