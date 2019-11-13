package orwir.gazzit.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import orwir.gazzit.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchBinding.inflate(inflater, container, false)
        .also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        .root
}

class SearchViewModel : ViewModel() {

}