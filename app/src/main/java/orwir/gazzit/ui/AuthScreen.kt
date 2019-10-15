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
import orwir.gazzit.R
import orwir.gazzit.databinding.FragmentAuthBinding
import orwir.gazzit.util.SingleLiveEvent
import orwir.gazzit.util.provide

class AuthFragment : Fragment() {

    private val viewModel: AuthViewModel by provide()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.authorizationEvent.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_authFragment_to_feedFragment)
        })
    }
}

class AuthViewModel : ViewModel() {

    val authorizationEvent = SingleLiveEvent<Any>()

    fun authorize() {
        authorizationEvent.call()
    }

}