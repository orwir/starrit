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
import orwir.gazzit.R
import orwir.gazzit.authorize
import orwir.gazzit.databinding.FragmentAuthorizationBinding
import orwir.gazzit.util.provide

class LoginFragment : Fragment() {

    private val loginViewModel by provide<LoginViewModel>()
    private val authViewModel by provide<AuthViewModel> { context!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentAuthorizationBinding>(
        inflater,
        R.layout.fragment_authorization,
        container,
        false
    ).let {
        it.viewModel = loginViewModel
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.authorizeEvent.observe(viewLifecycleOwner, Observer {
            authViewModel.authorize { context?.startActivity(it) }
        })
    }
}

class LoginViewModel : ViewModel() {

    val authorizeEvent = MutableLiveData<Any>()

    fun authorize() {
        authorizeEvent.postValue(Any())
    }

}