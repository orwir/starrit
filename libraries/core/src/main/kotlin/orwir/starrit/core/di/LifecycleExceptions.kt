package orwir.starrit.core.di

import androidx.lifecycle.LifecycleOwner
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.TypeQualifier
import org.koin.ext.getFullName

fun LifecycleOwner.setCurrentHost() {
    Scope.hostScopeId = getScopeId()
}

fun LifecycleOwner.setCurrentScreen() {
    Scope.screenScopeId = getScopeId()
}

fun LifecycleOwner.createScope() {
    val scope = GlobalContext.get().koin.createScope(getScopeId(), getScopeName())
    lifecycle.addObserver(ScopeObserver(scope))
}

inline fun <reified T> LifecycleOwner.injectFromApp(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = lazy { Scope.app.get<T>(qualifier, parameters) }

inline fun <reified T> LifecycleOwner.injectFromHost(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = lazy { Scope.host.get<T>(qualifier, parameters) }

inline fun <reified T> LifecycleOwner.injectFromScreen(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = lazy { Scope.screen.get<T>(qualifier, parameters) }

private fun LifecycleOwner.getScopeId() = this::class.getFullName() + "@" + System.identityHashCode(this)

private fun LifecycleOwner.getScopeName() = TypeQualifier(this::class)