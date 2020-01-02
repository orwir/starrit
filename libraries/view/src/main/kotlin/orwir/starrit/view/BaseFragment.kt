package orwir.starrit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

typealias FragmentInflater<VB> = (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    protected abstract val inflate: FragmentInflater<VB>
    protected val baseActivity: BaseActivity by lazy { requireActivity() as BaseActivity }

    open fun onBindView(binding: VB) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflate(inflater, container, false)
        .apply {
            lifecycleOwner = viewLifecycleOwner
            onBindView(this)
        }
        .root
}