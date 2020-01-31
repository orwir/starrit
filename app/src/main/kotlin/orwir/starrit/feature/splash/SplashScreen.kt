package orwir.starrit.feature.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel
import orwir.starrit.access.AccessRepository
import orwir.starrit.databinding.FragmentSplashBinding
import orwir.starrit.view.BaseFragment
import orwir.starrit.view.FragmentInflater
import orwir.starrit.view.extension.launchWhenResumed

class SplashFragment(private val navigation: SplashNavigation) : BaseFragment<FragmentSplashBinding>() {

    override val inflate: FragmentInflater<FragmentSplashBinding> = FragmentSplashBinding::inflate
    private val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchWhenResumed {
            delay(400)
            if (viewModel.hasAccess()) {
                navigation.openLatestFeed()
            } else {
                navigation.openAuthorization(true, logo to "logo")
            }
        }
    }
}

internal class SplashViewModel(private val access: AccessRepository) : ViewModel() {

    suspend fun hasAccess(): Boolean = access.state().valid()

}