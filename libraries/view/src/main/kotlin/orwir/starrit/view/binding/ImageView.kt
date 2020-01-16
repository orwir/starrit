package orwir.starrit.view.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import coil.ImageLoader
import coil.api.load
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable
import coil.transform.Transformation
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.view.extension.lifecycleOwner

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
        context.lifecycleOwner?.apply {
            load(source, preview, placeholder, error, transformations).observe(this, Observer { })
        }
    }

    fun ImageView.load(
        source: String,
        preview: String? = null,
        placeholder: Drawable? = null,
        error: Drawable? = null,
        transformations: List<Transformation>? = null
    ): LiveData<Boolean> = liveData {

        fun invokeRequest(url: String, placeholder: Drawable?) = load(url) {
            transformations?.let(::transformations)
            placeholder(placeholder)
            error(error)
        }

        emit(true)
        setImageDrawable(placeholder) // if preview/source empty or null
        invokeRequest(preview?.takeIf { it.isNotBlank() } ?: source, placeholder).await()
        if (preview?.isNotBlank() == true) {
            invokeRequest(source, drawable).await()
        }
        emit(false)
    }

    fun ImageView.load(image: String, config: LoadRequestBuilder.() -> Unit = {}): RequestDisposable =
        loader.load(context, image) {
            target(this@load)
            apply(config)
        }

}