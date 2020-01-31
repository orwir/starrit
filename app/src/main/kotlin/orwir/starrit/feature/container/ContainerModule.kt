package orwir.starrit.feature.container

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureContainerModule = module {

    viewModel { ContainerViewModel() }

}