package orwir.starrit.access

import org.koin.dsl.binds
import org.koin.dsl.module
import orwir.starrit.access.internal.AuthorizationService
import orwir.starrit.access.internal.BaseAccessInterceptor
import orwir.starrit.access.internal.BaseAccessRepository
import orwir.starrit.access.internal.TokenRepository
import orwir.starrit.core.extension.service

val libraryAccessModule = module {

    single<AccessRepository> { BaseAccessRepository() } binds arrayOf(
        TokenRepository::class,
        AuthorizationRepository::class
    )

    single<AccessInterceptor> { BaseAccessInterceptor() }

    single { service(get(), AuthorizationService::class.java) }

}