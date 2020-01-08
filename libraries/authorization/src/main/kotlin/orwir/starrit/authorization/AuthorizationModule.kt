package orwir.starrit.authorization

import org.koin.dsl.binds
import org.koin.dsl.module
import orwir.starrit.authorization.internal.AuthorizationService
import orwir.starrit.authorization.internal.BasicAuthorizationInterceptor
import orwir.starrit.authorization.internal.BasicAccessRepository
import orwir.starrit.authorization.internal.TokenRepository
import orwir.starrit.core.extension.service

val libAuthorizationModule = module {

    single<AccessRepository> {
        BasicAccessRepository()
    } binds arrayOf(TokenRepository::class, AuthorizationFlowRepository::class)

    single<AuthorizationInterceptor> {
        BasicAuthorizationInterceptor()
    }

    single<AuthorizationService> {
        service(get(), AuthorizationService::class.java)
    }

}