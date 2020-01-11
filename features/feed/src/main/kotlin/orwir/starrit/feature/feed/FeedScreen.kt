package orwir.starrit.feature.feed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.isActive
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import orwir.android.material.banner.BannerBuilder
import orwir.starrit.core.event.EventBus
import orwir.starrit.core.model.NetworkState
import orwir.starrit.feature.feed.databinding.FragmentFeedBinding
import orwir.starrit.feature.feed.internal.FeedAdapter
import orwir.starrit.feature.feed.internal.PostContentInflater
import orwir.starrit.listing.ListingRepository
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.Post
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater
import orwir.starrit.view.MarginItemDecoration
import orwir.starrit.view.extension.argument
import orwir.starrit.view.extension.launchWhenResumed
import orwir.starrit.view.extension.observe
import orwir.videoplayer.bindPlayer
import timber.log.Timber

private const val TYPE = "type"
private const val SORT = "sort"

class FeedFragment(navigation: Lazy<FeedNavigation>) : BaseFragment<FragmentFeedBinding>() {
    override val inflate: FragmentInflater<FragmentFeedBinding> = FragmentFeedBinding::inflate

    private val type: Feed.Type by argument(TYPE)
    private val sort: Feed.Sort by argument(SORT)

    private val player: ExoPlayer by inject()
    private val banners: EventBus.Medium by inject()
    private val navigation by navigation
    private val contentInflater: PostContentInflater by currentScope.inject { parametersOf(navigation.value) }
    private val viewModel: FeedViewModel by viewModel { parametersOf(type, sort) }

    override fun onBindView(binding: FragmentFeedBinding) {
        binding.viewModel = viewModel
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.bindPlayer(player)

        val adapter = FeedAdapter(contentInflater, viewModel::retry)
        posts.adapter = adapter
        posts.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.space).toInt()))

        observe(viewModel.posts, adapter::submitList)
        observe(viewModel.networkState, adapter::setNetworkState)

        //todo: be sure that stream is closed and event not saved anywhere after consumption
        launchWhenResumed {
            for (event in banners.stream) {
                Timber.d("[Event] received(): $event")
                handleBannerEvent(event)
                if (!isActive) {
                    Timber.d("[Event] coroutine scope closed.")
                    break
                }
            }
            Timber.d("[Event] reached end of scope.")
        }

        viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                Timber.d("[Event] OnLifecycleEvent(ON_DESTROY)")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("[Event] onDestroyView()")
    }

    private fun handleBannerEvent(event: Any?) {
        //todo: check event
        banner.removeAllViews()
        BannerBuilder(context!!)
            .setParent(banner)
            .setIcon(R.drawable.ic_account)
            .setMessage(R.string.authorization_revoked)
            .setPrimaryButton(R.string.authorize) { navigation.openAuthorization() }
            .show()
    }

}

internal class FeedViewModel(type: Feed.Type, sort: Feed.Sort, repository: ListingRepository) : ViewModel() {

    private val feed = repository.feed(type, sort, viewModelScope)

    val title: String = "${type.subreddit}/${sort.asParameter()}"
    val posts: LiveData<PagedList<Post>> = feed.posts
    val networkState: LiveData<NetworkState> = feed.networkState

    fun retry() {
        feed.retry.call()
    }

}