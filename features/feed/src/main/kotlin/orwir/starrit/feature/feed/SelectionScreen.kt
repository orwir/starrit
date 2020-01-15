package orwir.starrit.feature.feed

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fragment_selection.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import orwir.starrit.feature.feed.databinding.FragmentSelectionBinding
import orwir.starrit.listing.ListingRepository
import orwir.starrit.listing.feed.Feed
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater
import orwir.starrit.view.extension.argument

class SelectionFragment : BaseFragment<FragmentSelectionBinding>() {
    override val inflate: FragmentInflater<FragmentSelectionBinding> = FragmentSelectionBinding::inflate

    internal val type: Feed.Type by argument(FEED_TYPE)
    internal val sort: Feed.Sort by argument(FEED_SORT)

    private val viewModel: SelectionViewModel by viewModel { parametersOf(type, sort) }

    override fun onBindView(binding: FragmentSelectionBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val typesAdapter = ArrayAdapter<Feed.Type>(requireContext(), R.layout.view_dropdown_item, viewModel.types)
        dropdown_type.setAdapter(typesAdapter)
        dropdown_type.setText(type.toString(), false)

        val sortsAdapter = ArrayAdapter<Feed.Sort>(requireContext(), R.layout.view_dropdown_item, viewModel.sorts)
        dropdow_sort.setAdapter(sortsAdapter)
        dropdow_sort.setText(sort.toString(), false)
    }

}

class SelectionViewModel(type: Feed.Type, sort: Feed.Sort, repository: ListingRepository) : ViewModel() {

    val types = arrayOf(
        Feed.Type.Home,
        Feed.Type.Popular,
        Feed.Type.All
    )

    val sorts = Feed.Sort.values()

}