package orwir.gazzit.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import orwir.gazzit.common.extensions.arg
import orwir.gazzit.common.extensions.injectFromActivityScope
import orwir.gazzit.feed.databinding.FragmentFeedBinding
import orwir.gazzit.model.ListingType

private const val ARG_TYPE = "type"

class FeedFragment : Fragment() {

    private val type: ListingType by lazy { ListingType.parse(arg(ARG_TYPE)) }
    private val viewModel: FeedViewModel by viewModel { parametersOf(type) }
    private val navigation: FeedNavigation by injectFromActivityScope()
    private val adapter = FeedAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFeedBinding.inflate(inflater, container, false)
        .also {
            it.listing = type.toTitle()
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        posts.adapter = adapter
//        posts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
//
//        viewModel.posts.observe(viewLifecycleOwner, Observer {
//            adapter.submitList(it)
//        })
//
//        viewModel.searchClick.observe(viewLifecycleOwner, Observer {
//            findNavController().navigate(ListingFragmentDirections.toSearch())
//        })
    }

    private fun ListingType.toTitle() = when (this) {
        is ListingType.Subreddit -> getString(R.string.subreddit, this.subreddit)
        is ListingType.Best -> getString(R.string.best)
    }
}

internal class FeedViewModel(type: ListingType) : ViewModel() {

    fun openSearch() {
        TODO("not implemented yet")
    }


//    val posts = LivePagedListBuilder<String, Post>(
//        ListingDataSourceFactory(listing, viewModelScope),
//        pageConfig
//    ).build()
//
//    val searchClick = SingleLiveEvent<Unit>()
//
//    fun openSearch() {
//        searchClick.call()
//    }

}