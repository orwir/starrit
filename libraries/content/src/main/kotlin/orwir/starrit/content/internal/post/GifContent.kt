package orwir.starrit.content.internal.post

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import orwir.starrit.content.databinding.ViewContentGifBinding
import orwir.starrit.content.post.GifPost
import orwir.starrit.core.extension.observe
import orwir.starrit.view.binding.ImageViewBinding.load
import orwir.starrit.view.binding.setVisibleOrGone
import orwir.starrit.view.extension.onDetached

@Suppress("FunctionName")
internal fun PostContentBinder.GifContent(post: GifPost, parent: ViewGroup): View =
    ViewContentGifBinding
        .inflate(inflater, parent, true)
        .apply {
            gif.loadImageData(post, hud.progress)
            hud.fullscreen.setOnClickListener { navigation.openFullscreen(post) }

            val playing = MutableLiveData<Boolean>()

            container.setOnClickListener { playing.value = playing.value != true }
            gif.onDetached { playing.value = false }

            owner.observe(playing) {
                caption.setVisibleOrGone(!it)
                val imageUrl = if (it) post.gif else post.imageSource.url
                gif.load(imageUrl, placeholder = gif.drawable)
                    .observe(owner, Observer { loading -> hud.progress.setVisibleOrGone(loading) })
            }
        }
        .root