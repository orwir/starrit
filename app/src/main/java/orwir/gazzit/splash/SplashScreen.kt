package orwir.gazzit.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import orwir.gazzit.R
import orwir.gazzit.authorization.AuthorizationRepository
import orwir.gazzit.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    val viewModel: SplashViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSplashBinding.inflate(inflater, container, false)
        .also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(1000)
            findNavController().navigate(
                if (viewModel.isAuthorized()) {
                    R.id.action_splashFragment_to_profileFragment
                } else {
                    R.id.action_splashFragment_to_authorizationFragment
                }
            )
        }
    }
}

class SplashViewModel(private val authorizationRepository: AuthorizationRepository) : ViewModel() {

    fun isAuthorized(): Boolean = authorizationRepository.hasToken()

}