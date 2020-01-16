package orwir.starrit.feature.feed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_selection.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import orwir.starrit.core.event.LiveEvent
import orwir.starrit.core.livedata.combineLiveData
import orwir.starrit.feature.feed.databinding.FragmentSelectionBinding
import orwir.starrit.listing.feed.Feed
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater
import orwir.starrit.view.extension.argument
import orwir.starrit.view.extension.makeExposedDropdown
import orwir.starrit.view.extension.observe
import orwir.starrit.view.extension.selection

class SelectionFragment : BaseFragment<FragmentSelectionBinding>() {
    override val inflate: FragmentInflater<FragmentSelectionBinding> = FragmentSelectionBinding::inflate

    private val initialType: Feed.Type by argument(FEED_TYPE)
    private val initialSort: Feed.Sort by argument(FEED_SORT)
    private val viewModel: SelectionViewModel by viewModel { parametersOf(initialType, initialSort) }

    override fun onBindView(binding: FragmentSelectionBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dropdown_type.makeExposedDropdown(R.layout.view_dropdown_item, viewModel.types, initialType)
        dropdow_sort.makeExposedDropdown(R.layout.view_dropdown_item, viewModel.sorts, initialSort)
        val config = combineLiveData(dropdown_type.selection<Feed.Type>(), dropdow_sort.selection<Feed.Sort>())

        observe(config) { (type, sort) ->
            viewModel.updateSelection(type as Feed.Type, sort as Feed.Sort)
        }

        observe(viewModel.openSelectedEvent) {
            val direction = SelectionFragmentDirections.toFeedFragment(it.type, it.sort)
            findNavController().navigate(direction)
        }
    }

}

internal class SelectionViewModel(private val type: Feed.Type, private val sort: Feed.Sort) : ViewModel() {

    val types = arrayOf(
        Feed.Type.Home,
        Feed.Type.Popular,
        Feed.Type.All
    )
    val sorts = Feed.Sort.values()

    private val _selection = MutableLiveData<FeedConfig>()
    val selected: LiveData<Boolean> = _selection.map { it.type != type || it.sort != sort }
    val openSelectedEvent = LiveEvent<FeedConfig>()

    fun updateSelection(type: Feed.Type, sort: Feed.Sort) {
        _selection.postValue(FeedConfig(type, sort))
    }

    fun openSelected() {
        openSelectedEvent.value = _selection.value
    }

}

internal data class FeedConfig(val type: Feed.Type, val sort: Feed.Sort)