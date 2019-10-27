package orwir.gazzit.authorization

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.BuildConfig
import orwir.gazzit.service

val authorizationModule = module {

    single { service(get(), AuthorizationService::class.java) }

    single { AuthorizationRepository() }

    single { AuthorizationInterceptor() }

    viewModel { AuthorizationViewModel(get()) }

}

internal const val REDIRECT_URI = "${BuildConfig.SCHEMA}://${BuildConfig.HOST}"