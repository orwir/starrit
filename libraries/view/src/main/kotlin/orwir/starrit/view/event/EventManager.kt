package orwir.starrit.view.event

import android.content.Context
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import orwir.starrit.core.event.Event
import orwir.starrit.core.event.EventBus

class EventManager(
    private val low: EventBus.Low,
    private val medium: EventBus.Medium,
    private val high: EventBus.High
) {

    private val handlers = mutableMapOf<Event, EventHandler>()

    fun observeDialogs(owner: LifecycleOwner, context: Context) {
        high.flow.observe(owner, Observer { handler<EventHandler.Dialog>(it).handle(context) })
    }

    fun observeBanners(owner: LifecycleOwner, container: FrameLayout) {
        medium.flow.observe(owner, Observer { handler<EventHandler.Banner>(it).handle(container) })
    }

    fun observeSnackbars(owner: LifecycleOwner, container: CoordinatorLayout) {
        low.flow.observe(owner, Observer { handler<EventHandler.Snackbar>(it).handle(container) })
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : EventHandler> handler(event: Event): T {
        val handler = handlers[event] ?: throw IllegalStateException("Handler for event [$event] does not registered!")
        return handler as T
    }

}