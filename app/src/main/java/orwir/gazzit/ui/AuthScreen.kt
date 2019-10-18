package orwir.gazzit.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.dsl.module
import orwir.gazzit.R
import orwir.gazzit.auth.AuthRepository
import orwir.gazzit.databinding.FragmentAuthBinding
import orwir.gazzit.util.SingleLiveEvent
import orwir.gazzit.util.map

val authScreenModule = module {
    viewModel { AuthViewModel(get()) }
}

class AuthFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.authorizationEvent.observe(this, Observer {
            startActivity(Intent(Intent.ACTION_VIEW, it))
        })

        viewModel.hasValidToken.observe(this, Observer {
            if (it) {
                findNavController().navigate(R.id.action_authFragment_to_feedFragment)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil
        .inflate<FragmentAuthBinding>(inflater, R.layout.fragment_auth, container, false)
        .also {
            it.viewModel = viewModel
        }
        .root
}

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val authorizationEvent = SingleLiveEvent<Uri>()
    val hasValidToken: LiveData<Boolean> = authRepository.token.map { it != null }

    fun authorize() {
        authorizationEvent.postValue(authRepository.buildAuthUri())
    }
}