package orwir.gazzit.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import orwir.gazzit.databinding.FragmentProfileBinding
import java.lang.Exception

class ProfileFragment : Fragment() {

    val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentProfileBinding.inflate(inflater, container, false)
        .also {
            it.viewModel = viewModel
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.test()
    }
}

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    fun test() {
        viewModelScope.launch {
            try {
                val profile = profileRepository.profile()
                Log.d("!!!!", profile.toString())
            } catch (e: Exception) {
                Log.d("!!!!", e.message)
            }
        }
    }

}