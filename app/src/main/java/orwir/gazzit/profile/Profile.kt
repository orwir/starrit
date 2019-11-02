package orwir.gazzit.profile

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.common.service

val profileModule = module {

    single { service(get(), ProfileService::class.java) }

    single { ProfileRepository() }

    viewModel { ProfileViewModel(get()) }

}