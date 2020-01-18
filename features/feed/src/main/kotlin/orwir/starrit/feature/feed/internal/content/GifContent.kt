package orwir.starrit.feature.feed.internal.content

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import orwir.starrit.core.extension.observe
import orwir.starrit.feature.feed.databinding.ViewContentGifBinding
import orwir.starrit.listing.feed.GifPost
import orwir.starrit.view.binding.ImageViewBinding.load
import orwir.starrit.view.binding.setVisibleOrGone
import orwir.starrit.view.extension.onDetached

@Suppress("FunctionName")
internal fun PostContentBinder.GifContent(post: GifPost, parent: ViewGroup): View =
    ViewContentGifBinding
        .inflate(inflater, parent, true)
        .apply {
            post.loadImageData(gif, progress)

            val playing = MutableLiveData<Boolean>()

            container.setOnClickListener { playing.value = playing.value != true }
            gif.onDetached { playing.value = false }

            owner.observe(playing) {
                caption.setVisibleOrGone(!it)
                val imageUrl = if (it) post.gif else post.imageSource.url
                gif.load(imageUrl, placeholder = gif.drawable)
                    .observe(owner, Observer { loading -> progress.setVisibleOrGone(loading) })
            }
        }
        .root