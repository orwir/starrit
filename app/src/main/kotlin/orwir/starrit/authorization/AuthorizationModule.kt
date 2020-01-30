package orwir.starrit.authorization

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureAuthorizationModule = module {

    viewModel { AuthorizationViewModel(get()) }

}