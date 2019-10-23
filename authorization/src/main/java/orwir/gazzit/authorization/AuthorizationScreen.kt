package orwir.gazzit.authorization

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import orwir.gazzit.authorization.databinding.FragmentAuthorizationBinding

class AuthorizationFragment : Fragment() {

    private val viewModel: AuthorizationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.authorizationState.observe(this, Observer {
            when(it) {
                is Step.Init -> startActivity(Intent(Intent.ACTION_VIEW, it.uri))
                is Step.Success -> {}
                is Step.Failure -> {}
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAuthorizationBinding.inflate(inflater, container, false)
        .also {
            it.viewModel = viewModel
        }
        .root
}

class AuthorizationViewModel(private val flow: RequestToken) : ViewModel() {

    val authorizationState: LiveData<Step> = MutableLiveData()

    fun authorize() {
        viewModelScope.launch {
            flow().collect { (authorizationState as MutableLiveData).postValue(it) }
        }
    }

}