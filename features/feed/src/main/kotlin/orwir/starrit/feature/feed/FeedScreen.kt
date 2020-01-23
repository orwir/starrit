package orwir.starrit.feature.feed

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.coroutines.delay
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import orwir.starrit.access.isAccessDenied
import orwir.starrit.access.showAccessRevoked
import orwir.starrit.core.model.NetworkState
import orwir.starrit.feature.feed.databinding.FragmentFeedBinding
import orwir.starrit.feature.feed.internal.adapter.FeedAdapter
import orwir.starrit.feature.feed.internal.content.PostContentBinder
import orwir.starrit.listing.ListingRepository
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.Post
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater
import orwir.starrit.view.MarginItemDecoration
import orwir.starrit.view.extension.argument
import orwir.starrit.view.extension.launchWhenResumed
import orwir.starrit.view.extension.observe
import orwir.starrit.view.extension.showSnackbar
import orwir.videoplayer.bindVideoPlayer

class FeedFragment(navigation: Lazy<FeedNavigation>) : BaseFragment<FragmentFeedBinding>() {
    override val inflate: FragmentInflater<FragmentFeedBinding> = FragmentFeedBinding::inflate

    internal val type: Feed.Type by argument(FEED_TYPE)
    internal val sort: Feed.Sort by argument(FEED_SORT)
    internal val navigation by navigation

    private val preferences: FeedPreferences by inject()
    private val contentBinder: PostContentBinder by currentScope.inject()
    private val viewModel: FeedViewModel by viewModel { parametersOf(type, sort) }

    override fun onBindView(binding: FragmentFeedBinding) {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        currentScope.declare(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateMenu()
        viewLifecycleOwner.bindVideoPlayer { get() }

        val adapter = FeedAdapter(type, contentBinder, viewModel::retry)
        posts.adapter = adapter
        posts.addItemDecoration(MarginItemDecoration(requireContext(), R.dimen.space))

        observe(viewModel.posts, adapter::submitList)
        observe(viewModel.networkState) {
            adapter.setNetworkState(it)
            if (it is NetworkState.Failure) {
                handleFailure(it.error)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        preferences.latestType = type
        preferences.latestSort = sort
    }

    private fun inflateMenu() {
        toolbar.inflateMenu(R.menu.menu_feed)
        toolbar.menu.findItem(R.id.blur_nsfw).setIcon(blurNsfwIcon())
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.open_selection -> {
                    findNavController().navigate(FeedFragmentDirections.toSelectionFragment(type, sort))
                    true
                }
                R.id.blur_nsfw -> {
                    preferences.blurNsfw = !preferences.blurNsfw
                    launchWhenResumed {
                        delay(350)
                        it.setIcon(blurNsfwIcon())
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun handleFailure(exception: Exception) {
        if (exception.isAccessDenied()) {
            showAccessRevoked(banner) { navigation.openAuthorization() }
        } else {
            root.showSnackbar(exception)
        }
    }

    @DrawableRes
    private fun blurNsfwIcon(): Int =
        if (preferences.blurNsfw) R.drawable.ic_visibility_off else R.drawable.ic_visibility_on

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