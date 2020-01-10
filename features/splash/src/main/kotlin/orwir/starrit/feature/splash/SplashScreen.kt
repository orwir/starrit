package orwir.starrit.feature.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel
import orwir.starrit.authorization.AccessRepository
import orwir.starrit.feature.splash.databinding.FragmentSplashBinding
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater

class SplashFragment(navigation: Lazy<SplashNavigation>) : BaseFragment<FragmentSplashBinding>() {

    override val inflate: FragmentInflater<FragmentSplashBinding> = FragmentSplashBinding::inflate
    private val viewModel: SplashViewModel by viewModel()
    private val navigation by navigation

    override fun onBindView(binding: FragmentSplashBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            delay(200)
            if (viewModel.hasAccess()) {
                navigation.openLastFeed()
            } else {
                navigation.openLogin()
            }
        }
    }
}

internal class SplashViewModel(private val repository: AccessRepository) : ViewModel() {

    suspend fun hasAccess(): Boolean = repository.type().valid()

}