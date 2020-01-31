package orwir.starrit.connect

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.fragment_connect.*
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel
import orwir.starrit.access.AuthorizationRepository
import orwir.starrit.access.BuildConfig
import orwir.starrit.access.isAccessDenied
import orwir.starrit.access.model.Step
import orwir.starrit.access.showAccessDenied
import orwir.starrit.databinding.FragmentConnectBinding
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater
import orwir.starrit.view.extension.launchWhenResumed
import orwir.starrit.view.extension.observe
import orwir.starrit.view.extension.showSnackbar

class ConnectFragment(private val navigation: ConnectNavigation) : BaseFragment<FragmentConnectBinding>() {

    override val inflate: FragmentInflater<FragmentConnectBinding> = FragmentConnectBinding::inflate
    private val viewModel: ConnectViewModel by viewModel()

    override fun onBindView(binding: FragmentConnectBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onLinkDispatched {
            filter { it.toString().startsWith(BuildConfig.REDIRECT_URI) }
            callback { viewModel.authorize(it) }
        }

        observe(viewModel.state) {
            when (it) {
                is Step.Start -> navigation.openBrowser(it.uri)
                is Step.Success -> navigation.openDefaultFeed()
                is Step.Failure -> handleFailure(it.exception)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        launchWhenResumed { resetWhenUserCancelAuthorization() }
    }

    private fun handleFailure(exception: Exception) {
        if (exception.isAccessDenied()) {
            showAccessDenied(requireContext(), viewModel::authorize)
        } else {
            root.showSnackbar(exception)
        }
    }

    private suspend fun resetWhenUserCancelAuthorization() {
        delay(1000)
        if (viewModel.state.value is Step.Start) {
            viewModel.reset()
        }
    }

}

internal class ConnectViewModel(private val authorization: AuthorizationRepository) : ViewModel() {

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