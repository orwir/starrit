package orwir.starrit.core.event

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel

sealed class EventBus<T> {

    @ExperimentalCoroutinesApi
    private val channel: BroadcastChannel<T> = StatelessBroadcastChannel()

    @ExperimentalCoroutinesApi
    val stream: ReceiveChannel<T>
        get() = channel.openSubscription()

    @ExperimentalCoroutinesApi
    fun send(event: T) {
        channel.offer(event)
    }

    class Low : EventBus<Any>()
    class Medium : EventBus<Any>()
    class High : EventBus<Any>()

}