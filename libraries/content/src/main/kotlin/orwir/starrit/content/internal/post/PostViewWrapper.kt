package orwir.starrit.content.internal.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import orwir.starrit.content.databinding.ViewPostComplexBinding
import orwir.starrit.content.databinding.ViewPostSimpleBinding
import orwir.starrit.content.feed.Feed
import orwir.starrit.content.post.PostViewModel

internal class PostViewWrapper(
    inflater: LayoutInflater,
    parent: ViewGroup,
    attach: Boolean,
    private val type: Feed.Type
) {
    val binding: ViewDataBinding
    val root: View
    val content: FrameLayout

    init {
        if (type.composite) {
            val binding = ViewPostComplexBinding.inflate(inflater, parent, attach)
            this.binding = binding
            root = binding.root
            content = binding.content
        } else {
            val binding = ViewPostSimpleBinding.inflate(inflater, parent, attach)
            this.binding = binding
            root = binding.root
            content = binding.content
        }
    }

    fun setViewModel(viewModel: PostViewModel) {
        if (type.composite) {
            (binding as ViewPostComplexBinding).viewModel = viewModel
        } else {
            (binding as ViewPostSimpleBinding).viewModel = viewModel
        }
    }

}