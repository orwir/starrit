package orwir.starrit.feature.splash

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureSplashModule = module {

    viewModel { SplashViewModel(get()) }

}