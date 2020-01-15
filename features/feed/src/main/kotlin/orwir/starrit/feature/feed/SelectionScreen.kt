package orwir.starrit.feature.feed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import orwir.starrit.feature.feed.databinding.FragmentSelectionBinding
import orwir.starrit.listing.ListingRepository
import orwir.starrit.listing.feed.Feed
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater

class SelectionFragment : BaseFragment<FragmentSelectionBinding>() {
    override val inflate: FragmentInflater<FragmentSelectionBinding> = FragmentSelectionBinding::inflate

    private val viewModel: SelectionViewModel by viewModel()

    override fun onBindView(binding: FragmentSelectionBinding) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}

class SelectionViewModel(type: Feed.Type, sort: Feed.Sort, repository: ListingRepository) : ViewModel() {

}