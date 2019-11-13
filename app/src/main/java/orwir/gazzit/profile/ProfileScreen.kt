package orwir.gazzit.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import orwir.gazzit.common.UiResponse
import orwir.gazzit.common.handleException
import orwir.gazzit.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentProfileBinding.inflate(inflater, container, false)
        .also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.profile.observe(viewLifecycleOwner, Observer {
            when (it) {
                is UiResponse.Success -> {
                    Log.d("!!!!", it.result.toString())
                }
                is UiResponse.Failure -> handleException(it.exception)
            }
        })

        viewModel.requestProfile()
    }
}

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    val profile: LiveData<UiResponse<Profile>> = MutableLiveData()

    fun requestProfile() {
        viewModelScope.launch {
            try {
                UiResponse.Success(profileRepository.profile())
            } catch (exception: Exception) {
                UiResponse.Failure<Profile>(exception)
            }.let((profile as MutableLiveData)::postValue)
        }
    }

}