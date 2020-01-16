package orwir.starrit.feature.feed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_selection.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import orwir.starrit.core.event.LiveEvent
import orwir.starrit.feature.feed.databinding.FragmentSelectionBinding
import orwir.starrit.listing.feed.Feed
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater
import orwir.starrit.view.extension.argument
import orwir.starrit.view.extension.makeExposedDropdown
import orwir.starrit.view.extension.observe
import orwir.starrit.view.extension.selectionFlow

class SelectionFragment : BaseFragment<FragmentSelectionBinding>() {
    override val inflate: FragmentInflater<FragmentSelectionBinding> = FragmentSelectionBinding::inflate

    private val initialType: Feed.Type by argument(FEED_TYPE)
    private val initialSort: Feed.Sort by argument(FEED_SORT)
    private val viewModel: SelectionViewModel by viewModel { parametersOf(initialType, initialSort) }

    override fun onBindView(binding: FragmentSelectionBinding) {
        binding.viewModel = viewModel
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dropdown_type.makeExposedDropdown(R.layout.view_dropdown_item, viewModel.types, initialType)
        dropdow_sort.makeExposedDropdown(R.layout.view_dropdown_item, viewModel.sorts, initialSort)

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            val typesFlow = dropdown_type.selectionFlow<Feed.Type>()
            val sortsFlow = dropdow_sort.selectionFlow<Feed.Sort>()
            feedConfigFlow(typesFlow, sortsFlow).collect { viewModel.updateSelection(it) }
        }

        observe(viewModel.openSelectedEvent) {
            val direction = SelectionFragmentDirections.toFeedFragment(it.type, it.sort)
            findNavController().navigate(direction)
        }
    }

    @ExperimentalCoroutinesApi
    private fun feedConfigFlow(type: Flow<Feed.Type>, sort: Flow<Feed.Sort>): Flow<FeedConfig> =
        type.combine(sort) { t, s -> FeedConfig(t, s) }

}

internal class SelectionViewModel(
    private val type: Feed.Type,
    private val sort: Feed.Sort
) : ViewModel() {

    val types = arrayOf(
        Feed.Type.Home,
        Feed.Type.Popular,
        Feed.Type.All
    )
    val sorts = Feed.Sort.values()

    private val _selection = MutableLiveData<FeedConfig>()
    val selected: LiveData<Boolean> = _selection.map { (type, sort) -> this.type != type || this.sort != sort }
    val openSelectedEvent = LiveEvent<FeedConfig>()

    fun updateSelection(config: FeedConfig) {
        _selection.postValue(config)
    }

    fun openSelected() {
        openSelectedEvent.value = _selection.value
    }

}

internal data class FeedConfig(val type: Feed.Type, val sort: Feed.Sort)