package orwir.starrit.authorization

import org.koin.dsl.bind
import org.koin.dsl.module
import orwir.starrit.authorization.internal.AuthorizationService
import orwir.starrit.authorization.internal.BasicAuthorizationInterceptor
import orwir.starrit.authorization.internal.BasicAuthorizationRepository
import orwir.starrit.authorization.internal.TokenRepository
import orwir.starrit.core.extension.service

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