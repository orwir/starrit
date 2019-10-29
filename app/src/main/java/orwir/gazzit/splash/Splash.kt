package orwir.gazzit.splash

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {

    viewModel { SplashViewModel(get()) }

}