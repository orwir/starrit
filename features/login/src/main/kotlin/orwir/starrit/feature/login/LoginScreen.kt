package orwir.starrit.feature.login

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel
import orwir.starrit.authorization.AuthorizationRepository
import orwir.starrit.authorization.BuildConfig.REDIRECT_URI
import orwir.starrit.authorization.TokenException
import orwir.starrit.authorization.model.Step
import orwir.starrit.feature.login.databinding.FragmentLoginBinding
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater
import orwir.starrit.view.extension.launchWhenResumed
import orwir.starrit.view.extension.observe

class LoginFragment(navigation: Lazy<LoginNavigation>) : BaseFragment<FragmentLoginBinding>() {

    override val inflate: FragmentInflater<FragmentLoginBinding> = FragmentLoginBinding::inflate
    private val viewModel: LoginViewModel by viewModel()
    private val navigation by navigation

    override fun onBindView(binding: FragmentLoginBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onLinkDispatched {
            filter { it.toString().startsWith(REDIRECT_URI) }
            onLinkReceived { viewModel.authorize(it) }
        }

        observe(viewModel.state) {
            when (it) {
                is Step.Start -> navigation.openBrowser(it.uri)
                is Step.Success -> navigation.openHomeFeed()
                is Step.Failure -> handleFailure(it.exception)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        launchWhenResumed { resetWhenUserCancelAuthorization() }
    }

    private fun handleFailure(exception: Exception) {
        fun Exception.isAccessDenied() = this is TokenException && code == TokenException.ErrorCode.ACCESS_DENIED

//        showErrorDialog(exception) {
//            if (exception.isAccessDenied()) {
//                setTitle(R.string.authorization_required)
//                setMessage(R.string.error_message_access_denied)
//            }
//            setPositiveButton(R.string.retry) { _, _ ->
//                viewModel.authorize()
//            }
//        }
    }

    private suspend fun resetWhenUserCancelAuthorization() {
        delay(1000)
        if (viewModel.state.value is Step.Start) {
            viewModel.reset()
        }
    }

}

internal class LoginViewModel(private val authorization: AuthorizationRepository) : ViewModel() {

    val state: LiveData<Step> = authorization
        .flow()
        .asLiveData(viewModelScope.coroutineContext)

    val inProgress: LiveData<Boolean> = state.map { it is Step.Start || it is Step.Success }

    fun authorize() {
        authorization.startFlow()
    }

    fun authorize(uri: Uri) {
        authorization.completeFlow(uri)
    }

    fun reset() {
        authorization.resetFlow()
    }

    fun anonymous() {
        authorization.anonymousAccess()
    }

}