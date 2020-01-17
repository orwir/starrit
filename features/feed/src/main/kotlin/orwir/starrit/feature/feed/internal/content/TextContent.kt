package orwir.starrit.feature.feed.internal.content

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import orwir.starrit.core.livedata.LiveEvent
import orwir.starrit.feature.feed.databinding.ViewContentTextBinding
import orwir.starrit.listing.feed.TextPost

@Suppress("FunctionName")
internal fun PostContentBinder.TextContent(post: TextPost, parent: ViewGroup): View =
    ViewContentTextBinding
        .inflate(inflater, parent, true)
        .apply {
            val vm = TextViewModel(post)
            viewModel = vm
            lifecycleOwner = owner

            text.post { vm.validateCollapsed(text.lineCount) }
            vm.expandEvent.observe(owner, Observer { text.maxLines = Int.MAX_VALUE })
        }
        .root

internal class TextViewModel(post: TextPost) {

    val content = post.text
    val maxLines = 8
    val collapsed: LiveData<Boolean> = MutableLiveData()
    val expandEvent = LiveEvent<Unit>()

    fun validateCollapsed(lines: Int) {
        (collapsed as MutableLiveData).value = lines > maxLines
    }

    fun expand() {
        (collapsed as MutableLiveData).value = false
        expandEvent.call()
    }

}