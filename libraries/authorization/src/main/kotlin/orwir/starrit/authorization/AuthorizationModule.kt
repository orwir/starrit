package orwir.starrit.authorization

import org.koin.dsl.binds
import org.koin.dsl.module
import orwir.starrit.authorization.internal.AuthorizationService
import orwir.starrit.authorization.internal.BaseAccessInterceptor
import orwir.starrit.authorization.internal.BaseAccessRepository
import orwir.starrit.authorization.internal.TokenRepository
import orwir.starrit.core.extension.service

val libraryAuthorizationModule = module {

    single<AccessRepository> { BaseAccessRepository() } binds arrayOf(
        TokenRepository::class,
        AuthorizationRepository::class
    )

    single<AccessInterceptor> { BaseAccessInterceptor() }

    single { service(get(), AuthorizationService::class.java) }

}