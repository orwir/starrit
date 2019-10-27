package orwir.gazzit.authorization

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_authorization.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import orwir.gazzit.authorization.model.Step
import orwir.gazzit.databinding.FragmentAuthorizationBinding

class AuthorizationFragment : Fragment() {

    private val viewModel: AuthorizationViewModel by viewModel()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.authorizationState.observe(this, Observer {
            when (it) {
                is Step.Start -> {
                    enableUI(false)
                    startActivity(Intent(Intent.ACTION_VIEW, it.uri))
                }
                is Step.Success -> {
                    Snackbar.make(view!!, "token received", Snackbar.LENGTH_LONG).show()
                }
                is Step.Failure -> {
                    Snackbar.make(view!!, it.exception.toString(), Snackbar.LENGTH_LONG).show()
                    enableUI(true)
                }
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

    private fun enableUI(enable: Boolean) {
        authorize.isEnabled = enable
    }
}

class AuthorizationViewModel(private val repository: AuthorizationRepository) : ViewModel() {

    @ExperimentalCoroutinesApi
    val authorizationState: LiveData<Step> = repository
        .authorizationFlow()
        .asLiveData(context = viewModelScope.coroutineContext)

    fun authorize() {
        repository.startAuthorization()
    }
}