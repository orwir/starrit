package orwir.gazzit.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import orwir.gazzit.authorization.AuthorizationRepository
import orwir.gazzit.authorization.TokenException
import orwir.gazzit.common.extensions.injectFromActivityScope
import orwir.gazzit.splash.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by viewModel()
    private val navigation: SplashNavigation by injectFromActivityScope()

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
            if (viewModel.isAuthorized()) {
                navigation.openLatestFeed()
            } else {
                navigation.requestAuthorization()
            }
        }
    }
}

internal class SplashViewModel(
    private val authorizationRepository: AuthorizationRepository
) : ViewModel() {

    suspend fun isAuthorized(): Boolean =
        try {
            authorizationRepository.obtainToken()
            true
        } catch (e: TokenException) {
            false
        }

}