package orwir.gazzit.common.extensions

import androidx.fragment.app.Fragment
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> Fragment.argument(key: String): Lazy<T> = lazy {
    arguments?.get(key) as T
}