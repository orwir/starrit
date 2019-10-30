package orwir.gazzit.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import orwir.gazzit.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFeedBinding.inflate(inflater, container, false)
        .also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        .root
}

class FeedViewModel(private val repository: FeedRepository) : ViewModel() {

}