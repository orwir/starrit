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
import org.koin.android.viewmodel.ext.android.viewModel
import orwir.gazzit.databinding.FragmentListingBinding
import orwir.gazzit.listing.model.Post
import orwir.gazzit.listing.source.ListingDataSourceFactory
import orwir.gazzit.listing.source.pageConfig

class ListingFragment : Fragment() {

    private val viewModel: ListingViewModel by viewModel()
    private val adapter = ListingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentListingBinding.inflate(inflater, container, false)
        .also {
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
}

class ListingViewModel(private val repository: ListingRepository) : ViewModel() {

    val posts = LivePagedListBuilder<String, Post>(
        ListingDataSourceFactory("aww", viewModelScope),
        pageConfig
    ).build()

}