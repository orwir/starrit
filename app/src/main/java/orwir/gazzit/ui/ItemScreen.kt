package orwir.gazzit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.dsl.module
import orwir.gazzit.R
import orwir.gazzit.databinding.FragmentItemBinding

val itemScreenModule = module {
    viewModel { ItemViewModel() }
}

class ItemFragment : Fragment() {

    private val viewModel: ItemViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil
        .inflate<FragmentItemBinding>(inflater, R.layout.fragment_item, container, false)
        .also {
            it.viewModel = viewModel
        }
        .root
}

class ItemViewModel : ViewModel()