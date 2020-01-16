package orwir.starrit.feature.feed.internal.content

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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

            playing.observe(owner, Observer {
                caption.setVisibleOrGone(!it)
                val imageUrl = if (it) post.gif else post.source
                gif.load(imageUrl, placeholder = gif.drawable)
                    .observe(owner, Observer { loading -> progress.setVisibleOrGone(loading) })
            })
        }
        .root