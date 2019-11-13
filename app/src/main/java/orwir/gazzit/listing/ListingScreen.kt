package orwir.gazzit.listing

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
import kotlinx.android.synthetic.main.fragment_listing.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import orwir.gazzit.R
import orwir.gazzit.databinding.FragmentListingBinding
import orwir.gazzit.listing.source.ListingDataSourceFactory
import orwir.gazzit.listing.source.pageConfig

private const val ARG_LISTING = "listing"

class ListingFragment : Fragment() {

    private val listing by lazy { ListingType.parse(arguments!![ARG_LISTING] as String) }
    private val viewModel: ListingViewModel by viewModel { parametersOf(listing) }
    private val adapter = ListingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentListingBinding.inflate(inflater, container, false)
        .also {
            it.listing = listing.toTitle()
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
    }

    private fun ListingType.toTitle() = when(this) {
        is ListingType.Subreddit -> getString(R.string.subreddit, this.subreddit)
        is ListingType.Best -> getString(R.string.best)
    }
}

class ListingViewModel(listing: ListingType) : ViewModel() {

    val posts = LivePagedListBuilder<String, Post>(
        ListingDataSourceFactory(listing, viewModelScope),
        pageConfig
    ).build()

    fun openSearch() {

    }

}