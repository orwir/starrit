package orwir.gazzit.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import java.lang.IllegalArgumentException

inline fun <reified VM : ViewModel> provide(context: Any): Lazy<VM> = lazy { provideVM<VM>(context) }

inline fun <reified VM : ViewModel> provide(noinline wrapper: () -> Any): Lazy<VM> = lazy { provideVM<VM>(wrapper()) }

inline fun <reified VM : ViewModel> Fragment.provide(): Lazy<VM> = provide(this)

inline fun <reified VM : ViewModel> FragmentActivity.provide(): Lazy<VM> = provide(this)

inline fun <reified VM : ViewModel> provideVM(context: Any): VM = when(context) {
    is Fragment -> ViewModelProviders.of(context)[VM::class.java]
    is FragmentActivity -> ViewModelProviders.of(context)[VM::class.java]
    else -> throw IllegalArgumentException("Context type [${context.javaClass.name}] is not supported!")
}