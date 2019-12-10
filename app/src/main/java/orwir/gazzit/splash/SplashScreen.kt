package orwir.gazzit.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
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

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val direction = if (viewModel.isAuthorized()) {
                SplashFragmentDirections.toListing()
            } else {
                SplashFragmentDirections.toAuthorization()
            }
            findNavController().navigate(direction)
        }
    }
}

class SplashViewModel(private val authorizationRepository: AuthorizationRepository) : ViewModel() {

    fun isAuthorized(): Boolean = authorizationRepository.hasToken()

}