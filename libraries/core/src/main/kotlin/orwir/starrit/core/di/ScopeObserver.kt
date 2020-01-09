package orwir.starrit.core.di

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import org.koin.core.KoinApplication
import org.koin.core.scope.Scope

class ScopeObserver(val scope: Scope) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        scope.close()
        KoinApplication.logger.debug("$scope is closed.")
    }

}