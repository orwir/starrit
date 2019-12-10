package orwir.gazzit.authorization

import org.koin.dsl.module
import orwir.gazzit.common.AuthorizationInterceptor

val authorizationModule = module {

    single<AuthorizationRepository> {
        TODO("not implemented yet")
    }

    single<> {
        TODO("not implemented yet")
    }

    single<AuthorizationInterceptor> {
        TODO("not implemented yet")
    }

}