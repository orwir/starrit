package orwir.starrit.core.di

import androidx.lifecycle.LifecycleOwner
import org.koin.core.Koin
import org.koin.core.context.GlobalContext
import org.koin.core.error.NoScopeDefinitionFoundException
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.TypeQualifier
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeID
import org.koin.ext.getFullName
import kotlin.reflect.KClass

object DI {
    inline fun <reified T> dependency(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null
    ): T =
        Scopes.app.getOrNull<T>(qualifier, parameters)
            ?: Scopes.host.getOrNull<T>(qualifier, parameters)
            ?: Scopes.screen.get(qualifier, parameters)
}

object Scopes {

    private val koin: Koin
        get() = GlobalContext.get().koin

    val app: Scope
        get() = koin.rootScope

    val host: Scope
        get() = hostScopeId?.let(koin::getScope) ?: throw IllegalStateException("Current host is not set!")

    val screen: Scope
        get() = screenScopeId?.let(koin::getScope) ?: throw IllegalStateException("Current screen is not set!")

    internal var hostScopeId: ScopeID? = null
    internal var screenScopeId: ScopeID? = null

}

fun LifecycleOwner.setCurrentHost() {
    Scopes.hostScopeId = getScopeId()
}

fun LifecycleOwner.setCurrentScreen() {
    Scopes.screenScopeId = getScopeId()
}

fun LifecycleOwner.createScope() {
    try {
        val scope = GlobalContext.get().koin.createScope(getScopeId(), getScopeName())
        lifecycle.addObserver(ScopeObserver(scope))
    } catch (ignored: NoScopeDefinitionFoundException) {
        // ignored
    }
}

fun <T> LifecycleOwner.createScoped(
    clazz: KClass<*>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition? = null
) {
    val scope = GlobalContext.get().koin.getScope(getScopeId())
    scope.get<T>(clazz, qualifier, parameters)
}

inline fun <reified T> LifecycleOwner.inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = lazy { DI.dependency<T>(qualifier, parameters) }

inline fun <reified T> Injectable.inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = lazy { DI.dependency<T>(qualifier, parameters) }

private fun LifecycleOwner.getScopeId() = this::class.getFullName() + "@" + System.identityHashCode(this)

private fun LifecycleOwner.getScopeName() = TypeQualifier(this::class)