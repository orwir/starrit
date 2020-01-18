package orwir.starrit.core.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(source: LiveData<T>, observer: (T) -> Unit) {
    source.observe(this, Observer(observer))
}