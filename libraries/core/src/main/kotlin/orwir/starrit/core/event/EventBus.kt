package orwir.starrit.core.event

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import timber.log.Timber

sealed class EventBus<T> {

    @ExperimentalCoroutinesApi
    private val channel: BroadcastChannel<T> = BroadcastChannel(Channel.CONFLATED)

    @ExperimentalCoroutinesApi
    val stream: ReceiveChannel<T>
        get() = channel.openSubscription()

    @ExperimentalCoroutinesApi
    suspend fun send(event: T) {
        Timber.d("[Event] send(): severity=${this::class.simpleName}, payload=$event")
        channel.send(event)
    }

    class Low : EventBus<Any?>()
    class Medium : EventBus<Any?>()
    class High : EventBus<Any?>()

}