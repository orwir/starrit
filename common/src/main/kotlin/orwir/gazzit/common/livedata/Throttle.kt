package orwir.gazzit.common.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

fun <T> LiveData<T>.throttle(
    interval: Long,
    unit: TimeUnit = TimeUnit.MILLISECONDS,
    scope: CoroutineScope = GlobalScope
): LiveData<T> = Throttle(this, interval, unit, scope)

internal class Throttle<T>(
    source: LiveData<T>,
    interval: Long,
    unit: TimeUnit,
    scope: CoroutineScope
) : MediatorLiveData<T>() {

    @Volatile
    private var element: T? = null
    @Volatile
    private var job: Job? = null

    init {
        addSource(source) {
            element = it
            if (job == null) {
                job = scope.launch {
                    delay(unit.toMillis(interval))
                    postValue(element)
                    job = null
                }
            }
        }
    }

}