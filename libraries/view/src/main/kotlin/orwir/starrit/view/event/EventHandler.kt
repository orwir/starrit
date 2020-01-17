package orwir.starrit.view.event

import android.content.Context
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout

interface EventHandler {

    interface Dialog : EventHandler {
        fun handle(context: Context)
    }

    interface Banner : EventHandler {
        fun handle(container: FrameLayout)
    }

    interface Snackbar : EventHandler {
        fun handle(container: CoordinatorLayout)
    }

}