package orwir.starrit.feature.feed

import androidx.lifecycle.ViewModel
import orwir.starrit.feature.feed.databinding.FragmentContentBinding
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater

class ContentFragment : BaseFragment<FragmentContentBinding>() {
    override val inflate: FragmentInflater<FragmentContentBinding> = FragmentContentBinding::inflate
}

internal class ContentViewModel : ViewModel()