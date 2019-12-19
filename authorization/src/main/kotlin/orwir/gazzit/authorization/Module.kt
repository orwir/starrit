package orwir.gazzit.authorization

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.common.AuthorizationInterceptor
import orwir.gazzit.common.service

val authorizationModule = module {

    single<AuthorizationRepository> {
        BasicAuthorizationRepository()
    }

    single<AuthorizationService> {
        service(get(), AuthorizationService::class.java)
    }

    single<AuthorizationInterceptor> {
        BasicAuthorizationInterceptor()
    }

    viewModel { AuthorizationViewModel(get()) }

}