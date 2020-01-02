package orwir.starrit.feature.login

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import orwir.starrit.authorization.AuthorizationRepository
import orwir.starrit.authorization.BuildConfig.REDIRECT_URI
import orwir.starrit.authorization.model.Step
import orwir.starrit.feature.login.databinding.FragmentLoginBinding
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater
import orwir.starrit.view.activityScope

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    companion object {
        init {
            loadKoinModules(loginModule)
        }
    }

    override val inflate: FragmentInflater<FragmentLoginBinding> = FragmentLoginBinding::inflate
    private val viewModel: LoginViewModel by viewModel()
    private val navigation: LoginNavigation by activityScope()

    override fun onBindView(binding: FragmentLoginBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity.uriDispatcher.addCallback(viewLifecycleOwner) {
            ifUriStartsWith(REDIRECT_URI)
            onUri { viewModel.authorize(it) }
        }

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

    fun authorize(uri: Uri) {
        repository.authorizationFlowComplete(uri)
    }

}