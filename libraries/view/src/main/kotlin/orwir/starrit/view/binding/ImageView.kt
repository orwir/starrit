package orwir.starrit.view.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.transform.Transformation
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.view.extension.load

object ImageViewBinding : KoinComponent {

    private val loader: ImageLoader by inject()

    @JvmStatic
    @BindingAdapter("source", "preview", "placeholder", "error", "transformations", requireAll = false)
    fun ImageView.load(
        source: String,
        preview: String? = null,
        placeholder: Drawable? = null,
        error: Drawable? = null,
        transformations: List<Transformation>? = null
    ) {
        setImageDrawable(placeholder)
        load(preview?.takeIf { it.isNotBlank() } ?: source, loader) {
            transformations?.let(::transformations)
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

}