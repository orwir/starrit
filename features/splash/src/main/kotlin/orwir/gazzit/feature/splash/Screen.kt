package orwir.gazzit.feature.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import orwir.gazzit.authorization.AuthorizationRepository
import orwir.gazzit.feature.splash.databinding.FragmentSplashBinding
import orwir.gazzit.view.activityScope

class SplashFragment : Fragment() {

    companion object {
        init {
            loadKoinModules(splashModule)
        }
    }

    private val viewModel: SplashViewModel by viewModel()
    private val navigation: SplashNavigation by activityScope()

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
                navigation.openLastFeed()
            } else {
                navigation.openLogin()
            }
        }
    }
}

internal class SplashViewModel(private val repository: AuthorizationRepository) : ViewModel() {

    suspend fun isAuthorized(): Boolean = repository.isAuthorized()

}