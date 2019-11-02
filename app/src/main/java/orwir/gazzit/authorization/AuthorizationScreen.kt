package orwir.gazzit.authorization

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import orwir.gazzit.R
import orwir.gazzit.authorization.model.Step
import orwir.gazzit.databinding.FragmentAuthorizationBinding
import orwir.gazzit.common.handleException

class AuthorizationFragment : Fragment() {

    private val viewModel: AuthorizationViewModel by viewModel()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.authorizationState.observe(this, Observer {
            when (it) {
                is Step.Start -> {
                    startActivity(Intent(Intent.ACTION_VIEW, it.uri))
                }
                is Step.Success -> {
                    findNavController().navigate(R.id.action_authorizationFragment_to_feedFragment)
                }
                is Step.Failure -> {
                    handleException(it.exception)
                }
            }
        })
    }

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
}

class AuthorizationViewModel(private val repository: AuthorizationRepository) : ViewModel() {

    @ExperimentalCoroutinesApi
    val authorizationState: LiveData<Step> = repository
        .authorizationFlow()
        .asLiveData(context = viewModelScope.coroutineContext)
    @ExperimentalCoroutinesApi
    val requestInProgress: LiveData<Boolean> = authorizationState.map { it is Step.Start }

    fun authorize() {
        repository.startAuthorization()
    }
}