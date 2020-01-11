package orwir.starrit.view.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> Fragment.argument(key: String): Lazy<T> = lazy {
    arguments?.get(key) as T? ?: throw IllegalArgumentException("Argument [$key] not found!")
}

fun <T> Fragment.observe(stream: LiveData<T>, observer: (T) -> Unit) {
    stream.observe(viewLifecycleOwner, Observer(observer))
}

fun Fragment.launchWhenResumed(block: suspend CoroutineScope.() -> Unit): Job =
    viewLifecycleOwner.lifecycle.coroutineScope.launchWhenResumed(block)