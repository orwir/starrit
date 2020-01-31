package orwir.starrit.container

import androidx.lifecycle.ViewModel
import orwir.starrit.databinding.FragmentContentBinding
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater

class ContentFragment(private val navigation: ContentNavigation) : BaseFragment<FragmentContentBinding>() {

    override val inflate: FragmentInflater<FragmentContentBinding> = FragmentContentBinding::inflate

}

class ContentViewModel : ViewModel()