package orwir.gazzit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.dsl.module
import orwir.gazzit.R
import orwir.gazzit.auth.AuthRepository
import orwir.gazzit.databinding.FragmentSplashBinding
import orwir.gazzit.util.SingleLiveEvent

val splashScreenModule = module {
    viewModel { SplashViewModel(get()) }
}

class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigationEvent.observe(this, Observer {
            findNavController().navigate(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil
        .inflate<FragmentSplashBinding>(inflater, R.layout.fragment_splash, container, false)
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.postDelayed( viewModel::verifyAuthorization, 1000)
    }
}

class SplashViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val navigationEvent = SingleLiveEvent<Int>()

    fun verifyAuthorization() {
        navigationEvent.postValue(
            if (authRepository.token.value == null) {
                R.id.action_splashFragment_to_authFragment
            } else {
                R.id.action_splashFragment_to_feedFragment
            }
        )
    }

}