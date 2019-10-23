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
import orwir.gazzit.databinding.FragmentFeedBinding
import orwir.gazzit.feed.FeedRepository

val feedScreenModule = module {
    viewModel { FeedViewModel(get()) }
}

class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil
        .inflate<FragmentFeedBinding>(inflater, R.layout.fragment_feed, container, false)
        .also {
            it.viewModel = viewModel
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.feed()
    }
}

class FeedViewModel(private val feedRepository: FeedRepository) : ViewModel() {

    fun feed() {
        feedRepository.best()
    }

}