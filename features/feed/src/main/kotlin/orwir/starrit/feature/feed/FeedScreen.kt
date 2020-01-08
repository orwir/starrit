package orwir.starrit.feature.feed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.android.synthetic.main.fragment_feed.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf
import orwir.starrit.feature.feed.databinding.FragmentFeedBinding
import orwir.starrit.feature.feed.internal.FeedAdapter
import orwir.starrit.feature.feed.internal.FeedDataSourceFactory
import orwir.starrit.feature.feed.internal.pageConfig
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.Post
import orwir.starrit.view.*
import orwir.videoplayer.bindPlayer

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
    private val navigation: FeedNavigation by activityScope()
    private val adapter = FeedAdapter()
    private val player: ExoPlayer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindPlayer(player)
    }

    override fun onBindView(binding: FragmentFeedBinding) {
        binding.listing = "${type.title()}/${sort.asParameter()}"
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        posts.adapter = adapter
        posts.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.space).toInt()))

        viewModel.posts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun Feed.Type.title() = when (this) {
        is Feed.Type.Home -> getString(R.string.home)
        else -> subreddit
    }

}

internal class FeedViewModel(type: Feed.Type, sort: Feed.Sort) : ViewModel() {

    val posts = LivePagedListBuilder<String, Post>(FeedDataSourceFactory(type, sort, viewModelScope), pageConfig)
        .build()

}