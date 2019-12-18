package orwir.gazzit.common.extensions

import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.scope.currentScope
import orwir.gazzit.common.view.activity

inline fun <reified T> Fragment.activityScope(): Lazy<T> = lazy {
    activity?.currentScope?.get<T>() ?: throw IllegalStateException()
}

inline fun <reified T> View.activityScope(): Lazy<T> = lazy {
    activity().currentScope.get<T>()
}