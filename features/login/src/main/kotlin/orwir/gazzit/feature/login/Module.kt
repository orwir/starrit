package orwir.gazzit.feature.login

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val loginModule = module {

    viewModel { LoginViewModel(get()) }

}