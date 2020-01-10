package orwir.starrit.view.extension

import android.widget.ImageView
import coil.ImageLoader
import coil.api.load
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable

fun ImageView.load(url: String, loader: ImageLoader, builder: LoadRequestBuilder.() -> Unit = {}): RequestDisposable =
    loader.load(context, url) {
        target(this@load)
        apply(builder)
    }