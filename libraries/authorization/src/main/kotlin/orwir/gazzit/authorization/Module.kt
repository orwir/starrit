package orwir.gazzit.authorization

import org.koin.dsl.bind
import org.koin.dsl.module
import orwir.gazzit.authorization.internal.AuthorizationService
import orwir.gazzit.authorization.internal.BasicAuthorizationInterceptor
import orwir.gazzit.authorization.internal.BasicAuthorizationRepository
import orwir.gazzit.authorization.internal.TokenRepository
import orwir.gazzit.core.AuthorizationInterceptor
import orwir.gazzit.core.service

val libAuthorizationModule = module {

    single<AuthorizationRepository> {
        BasicAuthorizationRepository()
    } bind TokenRepository::class

    single<AuthorizationInterceptor> {
        BasicAuthorizationInterceptor()
    }

    single<AuthorizationService> {
        service(get(), AuthorizationService::class.java)
    }

}