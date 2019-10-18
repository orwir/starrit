package orwir.gazzit.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

fun <T> LiveData<T>.filter(predicate: (v: T) -> Boolean): LiveData<T> =
    object : MediatorLiveData<T>() {
        init {
            addSource(this@filter) {
                if (predicate(it)) {
                    value = it
                }
            }
        }
    }

fun <X, Y> LiveData<X>.map(transformer: (v: X) -> Y) = Transformations.map(this, transformer)