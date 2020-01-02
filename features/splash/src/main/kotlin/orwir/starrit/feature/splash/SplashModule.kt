package orwir.starrit.feature.splash

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val splashModule = module {

    viewModel { SplashViewModel(get()) }

}