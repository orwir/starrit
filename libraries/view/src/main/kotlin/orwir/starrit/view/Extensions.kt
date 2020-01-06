package orwir.starrit.view

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.api.load
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable
import java.io.Serializable

fun View.activity(context: Context = this.context): ComponentActivity {
    return when (context) {
        is ComponentActivity -> context
        is ContextWrapper -> activity(context.baseContext)
        else -> throw IllegalStateException("ComponentActivity not found!")
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> Fragment.argument(key: String): Lazy<T> = lazy {
    arguments?.get(key) as T? ?: throw IllegalArgumentException("Argument [$key] not found!")
}

fun ImageView.load(url: String, builder: LoadRequestBuilder.() -> Unit = {}): RequestDisposable {
    val loader = get<ImageLoader>()
    return loader.load(context, url) {
        target(this@load)
        apply(builder)
    }
}