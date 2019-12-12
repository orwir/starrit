package orwir.gazzit.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import orwir.gazzit.authorization.databinding.FragmentAuthorizationBinding
import orwir.gazzit.model.Step

class AuthorizationFragment : Fragment() {

    private val viewModel: AuthorizationViewModel by viewModel()
    private val navigation: AuthorizationNavigation by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAuthorizationBinding.inflate(inflater, container, false)
        .also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Step.Start -> navigation.openBrowser(it.uri)
                is Step.Success -> navigation.openFeed()
                is Step.Failure -> TODO("Not implemented yet! e:[${it.exception}]")
            }
        })
    }

}

internal class AuthorizationViewModel(
    private val repository: AuthorizationRepository
) : ViewModel() {

    val state: LiveData<Step> = repository
        .authorizationFlow()
        .asLiveData(context = viewModelScope.coroutineContext)
    val inProgress: LiveData<Boolean> = state.map { it is Step.Start }

    fun authorize() {
        repository.authorizationFlowStart()
    }

}