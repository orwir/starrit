package orwir.starrit.core.event

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel

@ExperimentalCoroutinesApi
class StatelessBroadcastChannel<T> constructor(
    private val broadcast: BroadcastChannel<T> = ConflatedBroadcastChannel()
) : BroadcastChannel<T> by broadcast {

    override fun openSubscription(): ReceiveChannel<T> = broadcast
        .openSubscription()
        .apply { poll() }

}