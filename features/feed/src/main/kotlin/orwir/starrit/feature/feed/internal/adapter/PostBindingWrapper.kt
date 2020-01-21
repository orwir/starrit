package orwir.starrit.feature.feed.internal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import orwir.starrit.feature.feed.databinding.ViewPostMultiBinding
import orwir.starrit.feature.feed.databinding.ViewPostSingleBinding
import orwir.starrit.listing.feed.Feed

internal class PostBindingWrapper(
    inflater: LayoutInflater,
    parent: ViewGroup,
    attach: Boolean,
    private val type: Feed.Type
) {
    val binding: ViewDataBinding
    val root: View
    val content: FrameLayout

    init {
        if (type.single) {
            val binding = ViewPostSingleBinding.inflate(inflater, parent, attach)
            this.binding = binding
            root = binding.root
            content = binding.content
        } else {
            val binding = ViewPostMultiBinding.inflate(inflater, parent, attach)
            this.binding = binding
            root = binding.root
            content = binding.content
        }
    }

    fun setViewModel(viewModel: PostViewModel) {
        if (type.single) {
            (binding as ViewPostSingleBinding).viewModel = viewModel
        } else {
            (binding as ViewPostMultiBinding).viewModel = viewModel
        }
    }

}