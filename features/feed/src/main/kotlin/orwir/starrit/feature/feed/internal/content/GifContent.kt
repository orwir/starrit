package orwir.starrit.feature.feed.internal.content

import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import orwir.starrit.feature.feed.databinding.ViewContentGifBinding
import orwir.starrit.listing.feed.GifPost
import orwir.starrit.view.binding.ImageViewBinding.load
import orwir.starrit.view.binding.setVisibleOrGone

@Suppress("FunctionName")
internal fun PostContentBinder.GifContent(post: GifPost): View =
    ViewContentGifBinding
        .inflate(inflater)
        .apply {
            val placeholder = gif.context.createImagePlaceholder(post)
            // todo: cancel on detach
            owner.lifecycleScope.launchWhenResumed {
                gif.load(post.source, post.preview, placeholder)
                    .collect {
                        progress.setVisibleOrGone(!it)
                    }
            }
            var playing = false
            container.setOnClickListener {
                owner.lifecycleScope.launchWhenResumed {
                    playing = !playing
                    caption.visibility = if (playing) View.GONE else View.VISIBLE
                    val url = if (playing) post.gif else post.source
                    progress.setVisibleOrGone(true)
                    gif.load(url) {
                        placeholder(gif.drawable)
                    }.await()
                    progress.setVisibleOrGone(false)
                }
            }
        }
        .root