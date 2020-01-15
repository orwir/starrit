package orwir.starrit.feature.feed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
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

    }

}

class SelectionViewModel(type: Feed.Type, sort: Feed.Sort, repository: ListingRepository) : ViewModel() {

}