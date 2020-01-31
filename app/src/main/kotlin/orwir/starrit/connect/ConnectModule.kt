package orwir.starrit.connect

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureConnectModule = module {

    viewModel { ConnectViewModel(get()) }

}