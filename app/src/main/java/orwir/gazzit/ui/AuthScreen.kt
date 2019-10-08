package orwir.gazzit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import orwir.gazzit.R
import orwir.gazzit.databinding.FragmentAuthBinding
import orwir.gazzit.util.provide

class LoginFragment : Fragment() {

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
}

class AuthViewModel : ViewModel() {

    fun authorize() {}

}