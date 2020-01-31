package orwir.starrit.container

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureContentModule = module {

    viewModel { ContentViewModel() }

}