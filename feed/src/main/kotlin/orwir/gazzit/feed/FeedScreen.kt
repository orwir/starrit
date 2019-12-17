package orwir.gazzit.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_feed.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import orwir.gazzit.common.extensions.arg
import orwir.gazzit.common.extensions.injectFromActivityScope
import orwir.gazzit.common.livedata.SingleLiveEvent
import orwir.gazzit.feed.databinding.FragmentFeedBinding
import orwir.gazzit.model.ListingType
import orwir.gazzit.model.Post

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
        posts.adapter = adapter
        posts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        viewModel.posts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.searchEvent.observe(viewLifecycleOwner, Observer {
            TODO("not implemented yet")
        })
    }

    private fun ListingType.toTitle() = when (this) {
        is ListingType.Subreddit -> getString(R.string.subreddit, this.subreddit)
        is ListingType.Best -> getString(R.string.best)
    }
}

internal class FeedViewModel(type: ListingType) : ViewModel() {

    val posts = LivePagedListBuilder<String, Post>(
        FeedDataSourceFactory(type, viewModelScope),
        pageConfig
    ).build()

    val searchEvent = SingleLiveEvent<Unit>()

    fun openSearch() {
        searchEvent.call()
    }

}