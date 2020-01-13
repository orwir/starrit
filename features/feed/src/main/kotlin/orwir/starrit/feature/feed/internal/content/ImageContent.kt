package orwir.starrit.feature.feed.internal.content

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import orwir.starrit.feature.feed.databinding.ViewContentImageBinding
import orwir.starrit.listing.feed.ImagePost
import orwir.starrit.view.binding.ImageViewBinding.load
import orwir.starrit.view.binding.setVisibleOrGone

@Suppress("FunctionName")
internal fun PostContentBinder.ImageContent(post: ImagePost, parent: ViewGroup): View =
    ViewContentImageBinding
        .inflate(inflater, parent, true)
        .apply {
            val placeholder = image.context.createImagePlaceholder(post)
            owner.lifecycleScope.launch {
                image.load(post.source, post.preview, placeholder)
                    .collect {
                        progress.setVisibleOrGone(!it)
                    }
            }
        }
        .root