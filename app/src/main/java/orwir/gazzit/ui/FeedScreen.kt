package orwir.gazzit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import orwir.gazzit.R
import orwir.gazzit.databinding.FragmentFeedBinding
import orwir.gazzit.util.provide

class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by provide()

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
}

class FeedViewModel : ViewModel()