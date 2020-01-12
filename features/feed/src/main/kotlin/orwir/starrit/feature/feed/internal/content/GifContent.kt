package orwir.starrit.feature.feed.internal.content

import android.view.View
import orwir.starrit.feature.feed.databinding.ViewContentGifBinding
import orwir.starrit.listing.feed.GifPost
import orwir.starrit.view.extension.load

//todo: improve gif content layout

@Suppress("FunctionName")
internal fun PostContentBinder.GifContent(post: GifPost): View =
    ViewContentGifBinding
        .inflate(inflater)
        .apply {
            this.post = post
            listener = View.OnClickListener {
                gif.load(if (playing == true) post.source else post.gif, imageLoader) {
                    placeholder(gif.drawable)
                }
                playing = playing?.let { !it } ?: true
            }
        }
        .root