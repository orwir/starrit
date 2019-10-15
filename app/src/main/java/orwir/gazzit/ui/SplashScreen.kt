package orwir.gazzit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import orwir.gazzit.R
import orwir.gazzit.databinding.FragmentSplashBinding
import orwir.gazzit.util.SingleLiveEvent
import orwir.gazzit.util.provide

class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by provide()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil
        .inflate<FragmentSplashBinding>(inflater, R.layout.fragment_splash, container, false)
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(it)
        })

        view.postDelayed({ viewModel.verifyAuthorization() }, 2000)
    }
}

class SplashViewModel : ViewModel() {

    val navigationEvent = SingleLiveEvent<Int>()

    fun verifyAuthorization() {
        navigationEvent.postValue(
            if (Math.random() > 0.5) {
                R.id.action_splashFragment_to_authFragment
            } else {
                R.id.action_splashFragment_to_feedFragment
            }
        )
    }

}