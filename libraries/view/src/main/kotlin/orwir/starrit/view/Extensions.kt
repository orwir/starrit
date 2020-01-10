package orwir.starrit.view

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import coil.ImageLoader
import coil.api.load
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable
import orwir.starrit.core.di.DI
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> Fragment.argument(key: String): Lazy<T> = lazy {
    arguments?.get(key) as T? ?: throw IllegalArgumentException("Argument [$key] not found!")
}

fun <T> Fragment.observe(stream: LiveData<T>, observer: (T) -> Unit) {
    stream.observe(viewLifecycleOwner, Observer(observer))
}

fun ImageView.load(url: String, builder: LoadRequestBuilder.() -> Unit = {}): RequestDisposable {
    val loader: ImageLoader = DI.dependency()
    return loader.load(context, url) {
        target(this@load)
        apply(builder)
    }
}