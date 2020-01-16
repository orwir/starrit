package orwir.starrit.core.livedata

import android.util.SparseArray
import androidx.core.util.set
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun combineLiveData(vararg sources: LiveData<*>): LiveData<Array<*>> = Combine(sources)

internal class Combine(sources: Array<out LiveData<*>>) : MediatorLiveData<Array<*>>() {

    private val collector = SparseArray<Any?>()

    init {
        sources.forEachIndexed { index, source ->
            addSource(source) { value ->
                collector[index] = value
                if (collector.size() == sources.size) { //all sources should post data at least once
                    postValue(Array(collector.size()) { i -> collector[i] })
                }
            }
        }
    }

}