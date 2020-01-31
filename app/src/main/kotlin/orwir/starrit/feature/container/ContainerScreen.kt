package orwir.starrit.feature.container

import androidx.lifecycle.ViewModel
import orwir.starrit.databinding.FragmentContainerBinding
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater

class ContainerFragment(private val navigation: ContainerNavigation) : BaseFragment<FragmentContainerBinding>() {

    override val inflate: FragmentInflater<FragmentContainerBinding> = FragmentContainerBinding::inflate

}

internal class ContainerViewModel : ViewModel()