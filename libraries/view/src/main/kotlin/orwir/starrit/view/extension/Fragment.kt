package orwir.starrit.view.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> Fragment.argument(key: String): Lazy<T> = lazy {
    arguments?.get(key) as T? ?: throw IllegalArgumentException("Argument [$key] not found!")
}

fun <T> Fragment.observe(stream: LiveData<T>, observer: (T) -> Unit) {
    stream.observe(viewLifecycleOwner, Observer(observer))
}