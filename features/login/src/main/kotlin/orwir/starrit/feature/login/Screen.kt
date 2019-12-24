package orwir.starrit.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import orwir.starrit.authorization.AuthorizationRepository
import orwir.starrit.authorization.model.Step
import orwir.starrit.feature.login.databinding.FragmentLoginBinding
import orwir.starrit.view.activityScope

class LoginFragment : Fragment() {

    companion object {
        init {
            loadKoinModules(loginModule)
        }
    }

    private val viewModel: LoginViewModel by viewModel()
    private val navigation: LoginNavigation by activityScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater, container, false)
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
                is Step.Success -> navigation.openHomePage()
                is Step.Failure -> TODO("Not implemented yet! e:[${it.exception}]")
            }
        })
    }

}

internal class LoginViewModel(private val repository: AuthorizationRepository) : ViewModel() {

    val state: LiveData<Step> = repository
        .authorizationFlow()
        .asLiveData(viewModelScope.coroutineContext)

    val inProgress: LiveData<Boolean> = state.map { it is Step.Start }

    fun authorize() {
        repository.authorizationFlowStart()
    }

}