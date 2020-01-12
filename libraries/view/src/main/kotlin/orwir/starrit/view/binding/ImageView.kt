package orwir.starrit.view.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
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
    ): LiveData<Boolean> = object : LiveData<Boolean>() {

        init {
            postValue(true)
            loadInternal(source, preview, placeholder, error, transformations)
        }

        private fun loadInternal(
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
                target(onError = ::onError) {
                    setImageDrawable(it)
                    if (preview?.isNotBlank() == true) {
                        loadInternal(source, null, it, error, transformations)
                    } else {
                        postValue(false)
                    }
                }
            }
        }

        private fun onError(error: Drawable?) {
            setImageDrawable(error)
            postValue(false)
        }

    }

}