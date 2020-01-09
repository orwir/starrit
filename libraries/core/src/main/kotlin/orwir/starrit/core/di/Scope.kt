package orwir.starrit.core.di

import org.koin.core.Koin
import org.koin.core.context.GlobalContext
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeID

object Scope {

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