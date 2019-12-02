package orwir.gazzit.videoplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.android.ext.android.inject
import orwir.gazzit.databinding.FragmentVideoplayerBinding

class VideoPlayerFragment : Fragment() {

    private val viewModel: VideoPlayerViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentVideoplayerBinding.inflate(inflater, container, false)
        .also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        .root
}

class VideoPlayerViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }

}