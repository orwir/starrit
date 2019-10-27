package orwir.gazzit.authorization

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.lazyService

val authorizationModule = module {

    single<Lazy<AuthorizationService>> { lazyService(get(), AuthorizationService::class.java) }

    single { AuthorizationRepository(get()) }

    single { AuthorizationInterceptor(get()) }

    viewModel { AuthorizationViewModel(get()) }

}

internal const val CLIENT_ID = "we9xZjW_b19qKQ"
internal const val CREDENTIALS_B64 = "d2U5eFpqV19iMTlxS1E6"
internal const val AUTHORITY = "oauth"
internal const val REDIRECT_URI = "gazzit://$AUTHORITY"
internal const val GRANT_TYPE = "authorization_code"