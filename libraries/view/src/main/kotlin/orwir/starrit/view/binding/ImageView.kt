package orwir.starrit.view.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.api.load
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable
import coil.transform.Transformation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

object ImageViewBinding : KoinComponent {

    private val loader: ImageLoader by inject()

    @JvmStatic
    @BindingAdapter("source", "preview", "placeholder", "error", "transformations", requireAll = false)
    fun ImageView.setImageData(
        source: String,
        preview: String? = null,
        placeholder: Drawable? = null,
        error: Drawable? = null,
        transformations: List<Transformation>? = null
    ) {
        GlobalScope.launch { load(source, preview, placeholder, error, transformations).collect { } }
    }

    fun ImageView.load(
        source: String,
        preview: String? = null,
        placeholder: Drawable? = null,
        error: Drawable? = null,
        transformations: List<Transformation>? = null
    ): Flow<Boolean> = flow {

        fun invokeRequest(url: String, placeholder: Drawable?) = loader.load(context, url) {
            transformations?.let(::transformations)
            placeholder(placeholder)
            error(error)
            target(this@load)
        }

        emit(false)
        invokeRequest(preview?.takeIf { it.isNotBlank() } ?: source, placeholder).await()
        if (preview?.isNotBlank() == true) {
            invokeRequest(source, drawable).await()
        }
        emit(true)
    }

    fun ImageView.load(image: String, config: LoadRequestBuilder.() -> Unit = {}): RequestDisposable =
        loader.load(context, image) {
            target(this@load)
            apply(config)
        }

}