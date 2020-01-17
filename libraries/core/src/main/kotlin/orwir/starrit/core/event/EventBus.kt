package orwir.starrit.core.event

import androidx.lifecycle.LiveData

sealed class EventBus<T> {

    val flow: LiveData<T> = LiveEvent()

    fun send(event: T) {
        (flow as LiveEvent).postValue(event)
    }

    class Low : EventBus<Event>()
    class Medium : EventBus<Event>()
    class High : EventBus<Event>()

}