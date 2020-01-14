package orwir.starrit.feature.feed.internal.content

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import orwir.starrit.feature.feed.databinding.ViewContentGifBinding
import orwir.starrit.listing.feed.GifPost
import orwir.starrit.view.binding.ImageViewBinding.load
import orwir.starrit.view.binding.setVisibleOrGone

@Suppress("FunctionName")
internal fun PostContentBinder.GifContent(post: GifPost, parent: ViewGroup): View =
    ViewContentGifBinding
        .inflate(inflater, parent, true)
        .apply {
            post.loadImageData(gif, progress)

            var playing = false
            container.setOnClickListener {
                owner.lifecycleScope.launch {
                    playing = !playing

                    caption.visibility = if (playing) View.GONE else View.VISIBLE
                    val imageUrl = if (playing) post.gif else post.source
                    gif.load(imageUrl, placeholder = gif.drawable).collect { progress.setVisibleOrGone(it) }
                }
            }
        }
        .root