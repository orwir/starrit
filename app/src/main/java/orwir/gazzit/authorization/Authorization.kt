package orwir.gazzit.authorization

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.BuildConfig
import orwir.gazzit.common.AuthorizationInterceptor
import orwir.gazzit.common.service

val authorizationModule = module {

    single { service(get(), AuthorizationService::class.java) }

    single { AuthorizationRepository() }

    single<AuthorizationInterceptor> { RedditAuthorizationInterceptor() }

    viewModel { AuthorizationViewModel(get()) }

}

internal const val REDIRECT_URI = "${BuildConfig.SCHEMA}://${BuildConfig.HOST}"