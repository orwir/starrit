package orwir.starrit.feature.feed.internal.content

import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import orwir.starrit.feature.feed.databinding.ViewContentImageBinding
import orwir.starrit.listing.feed.ImagePost
import orwir.starrit.view.binding.ImageViewBinding.load
import orwir.starrit.view.binding.setVisibleOrGone

@Suppress("FunctionName")
internal fun PostContentBinder.ImageContent(post: ImagePost): View =
    ViewContentImageBinding
        .inflate(inflater)
        .apply {
            val placeholder = image.context.createImagePlaceholder(post)
            // todo: cancel on detach
            owner.lifecycleScope.launchWhenResumed {
                image.load(post.source, post.preview, placeholder)
                    .collect {
                        progress.setVisibleOrGone(!it)
                    }
            }
        }
        .root