package orwir.starrit.feature.feed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.android.synthetic.main.fragment_feed.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf
import orwir.starrit.core.di.inject
import orwir.starrit.core.model.NetworkState
import orwir.starrit.feature.feed.databinding.FragmentFeedBinding
import orwir.starrit.feature.feed.internal.FeedAdapter
import orwir.starrit.listing.ListingRepository
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.Post
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater
import orwir.starrit.view.MarginItemDecoration
import orwir.starrit.view.argument
import orwir.videoplayer.bindPlayer
import timber.log.Timber

private const val TYPE = "type"
private const val SORT = "sort"

class FeedFragment : BaseFragment<FragmentFeedBinding>() {

    companion object {
        init {
            loadKoinModules(feedModule)
        }
    }

    override val inflate: FragmentInflater<FragmentFeedBinding> = FragmentFeedBinding::inflate
    private val type: Feed.Type by argument(TYPE)
    private val sort: Feed.Sort by argument(SORT)
    private val viewModel: FeedViewModel by viewModel { parametersOf(type, sort) }
    private val navigation: FeedNavigation by inject()
    private val player: ExoPlayer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindPlayer(player)
    }

    override fun onBindView(binding: FragmentFeedBinding) {
        binding.listing = "${type.subreddit}/${sort.asParameter()}"
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FeedAdapter(viewModel::retry)
        posts.adapter = adapter
        posts.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.space).toInt()))

        viewModel.posts.observe(viewLifecycleOwner, Observer(adapter::submitList))
        viewModel.networkState.observe(viewLifecycleOwner, Observer(adapter::setNetworkState))
    }

}

internal class FeedViewModel(type: Feed.Type, sort: Feed.Sort, repository: ListingRepository) : ViewModel() {

    private val feed = repository.feed(type, sort, viewModelScope)

    val posts: LiveData<PagedList<Post>> = feed.posts
    val networkState: LiveData<NetworkState> = feed.networkState

    fun retry() {
        feed.retry.call()
    }

    fun test() {
        Timber.d("[Banner] test click")
    }

}