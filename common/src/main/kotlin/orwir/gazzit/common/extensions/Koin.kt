package orwir.gazzit.common.extensions

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import org.koin.androidx.scope.currentScope
import org.koin.core.context.GlobalContext
import orwir.gazzit.common.view.activity

inline fun <reified T> Fragment.activityScope(): Lazy<T> = lazy {
    activity?.currentScope?.get<T>() ?: throw IllegalStateException()
}

inline fun <reified T> View.activityScope(): Lazy<T> = lazy {
    activity().currentScope.get<T>()
}

inline fun <reified T> View.get(): T = GlobalContext.get().koin.get()

inline fun <reified T> ViewDataBinding.activityScope(): Lazy<T> = root.activityScope()

inline fun <reified T> ViewDataBinding.get(): T = GlobalContext.get().koin.get()