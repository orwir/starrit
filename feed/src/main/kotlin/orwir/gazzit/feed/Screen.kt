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
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.android.synthetic.main.fragment_feed.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import orwir.gazzit.common.extensions.activityScope
import orwir.gazzit.common.extensions.argument
import orwir.gazzit.common.livedata.SingleLiveEvent
import orwir.gazzit.feed.databinding.FragmentFeedBinding
import orwir.gazzit.feed.model.FeedType
import orwir.gazzit.feed.model.Post
import orwir.videoplayer.bindPlayer

private const val TYPE = "type"

class FeedFragment : Fragment() {

    private val type: FeedType by argument(TYPE)
    private val viewModel: FeedViewModel by viewModel { parametersOf(type) }
    private val navigation: FeedNavigation by activityScope()
    private val adapter = FeedAdapter()
    private val player: ExoPlayer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindPlayer(player)
    }

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

    private fun FeedType.toTitle() = when (this) {
        is FeedType.Best -> getString(R.string.best)
        is FeedType.Subreddit -> getString(R.string.subreddit, name)
    }
}

internal class FeedViewModel(type: FeedType) : ViewModel() {

    val posts = LivePagedListBuilder<String, Post>(
        FeedDataSourceFactory(type, viewModelScope),
        pageConfig
    ).build()

    val searchEvent = SingleLiveEvent<Unit>()

    fun openSearch() {
        searchEvent.call()
    }

}