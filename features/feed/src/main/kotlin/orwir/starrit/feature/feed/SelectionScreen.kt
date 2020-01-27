package orwir.starrit.feature.feed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_selection.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import orwir.starrit.content.feed.Feed.*
import orwir.starrit.core.livedata.LiveEvent
import orwir.starrit.core.livedata.combineLiveData
import orwir.starrit.feature.feed.databinding.FragmentSelectionBinding
import orwir.starrit.feature.feed.internal.SearchAdapter
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater
import orwir.starrit.view.binding.setVisibleOrGone
import orwir.starrit.view.extension.*

class SelectionFragment : BaseFragment<FragmentSelectionBinding>() {
    override val inflate: FragmentInflater<FragmentSelectionBinding> = FragmentSelectionBinding::inflate

    private val initialType: Type by argument(FEED_TYPE)
    private val initialSort: Sort by argument(FEED_SORT)
    private val searchAdapter: SearchAdapter by currentScope.inject()
    private val viewModel: SelectionViewModel by viewModel { parametersOf(initialType, initialSort) }

    override fun onCreate(savedInstanceState: Bundle?) {
        currentScope.declare(this)
        super.onCreate(savedInstanceState)
    }

    override fun onBindView(binding: FragmentSelectionBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dropdown_type.makeExposedDropdown(R.layout.view_dropdown_item, viewModel.types, initialType)
        dropdow_sort.makeExposedDropdown(R.layout.view_dropdown_item, viewModel.sorts, initialSort)
        val config = combineLiveData(dropdown_type.selection<Type>(), dropdow_sort.selection<Sort>())

        search.setAdapter(searchAdapter)
        search.threshold = 3
        search.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) v.hideKeyboard()
        }
        if (initialType is Type.Subreddit) {
            viewModel.updateSubreddit(initialType.toString())
            search.setText(initialType.toString(), true)
        }

        observe(config) { (type, sort) ->
            viewModel.updateSelection(type as Type, sort as Sort)
            search_layout.setVisibleOrGone(type is Type.Subreddit)
            if (search.text.isNotBlank() && type !is Type.Subreddit) {
                viewModel.resetSubreddit()
                search.clearListSelection()
                search.setText("", true)
            }
        }

        observe(search.selection<String>(false)) {
            search.hideKeyboard()
            dropdown_type.setText(it, false)
            viewModel.updateSubreddit(it)
        }

        observe(viewModel.openSelectedEvent) {
            val direction = SelectionFragmentDirections.toFeedFragment(it.type, it.sort)
            findNavController().navigate(direction)
        }
    }

}

internal class SelectionViewModel(private val type: Type, private val sort: Sort) : ViewModel() {

    private val dummySubreddit = Type.Subreddit()

    val types = arrayOf(
        Type.Home,
        Type.Popular,
        Type.All,
        dummySubreddit
    )
    val sorts = Sort.values()

    private val _selection = MutableLiveData<FeedConfig>()
    val selected: LiveData<Boolean> = _selection.map { it.type.isNew() || it.sort != sort }
    val openSelectedEvent = LiveEvent<FeedConfig>()

    fun updateSelection(type: Type, sort: Sort) {
        _selection.postValue(FeedConfig(type, sort))
    }

    fun updateSubreddit(subreddit: String) {
        types[3] = Type.Subreddit(subreddit)
        updateSelection(types[3], _selection.value?.sort ?: sort)
    }

    fun resetSubreddit() {
        types[3] = dummySubreddit
    }

    fun openSelected() {
        openSelectedEvent.value = _selection.value
    }

    private fun Type.isNew(): Boolean {
        return this != type && this != dummySubreddit
    }

}

internal data class FeedConfig(val type: Type, val sort: Sort)