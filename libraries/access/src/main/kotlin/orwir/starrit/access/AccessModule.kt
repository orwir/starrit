package orwir.starrit.access

import org.koin.dsl.binds
import org.koin.dsl.module
import orwir.starrit.access.internal.*
import orwir.starrit.core.extension.service

val libraryAccessModule = module {

    single<AccessRepository> { BaseAccessRepository() } binds arrayOf(
        TokenRepository::class,
        AuthorizationRepository::class
    )

    single<AccessInterceptor> { BaseAccessInterceptor() }

    single { service(get(), AuthorizationService::class.java) }

    single { service(get(), AccountService::class.java) }

}