package orwir.starrit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import orwir.starrit.core.di.createScope
import orwir.starrit.core.di.setCurrentScreen
import orwir.starrit.core.link.LinkCallbackBuilder

typealias FragmentInflater<VB> = (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    protected abstract val inflate: FragmentInflater<VB>

    open fun onBindView(binding: VB) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createScope()
    }

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

    override fun onStart() {
        super.onStart()
        setCurrentScreen()
    }

    fun onLinkDispatched(handler: LinkCallbackBuilder.() -> Unit) {
        (requireActivity() as BaseActivity).linkDispatcher.addCallback(this, handler)
    }

}