package orwir.starrit.feature.feed.internal.content

import android.view.View
import android.view.ViewGroup
import orwir.starrit.feature.feed.databinding.ViewContentImageBinding
import orwir.starrit.listing.feed.ImagePost

@Suppress("FunctionName")
internal fun PostContentBinder.ImageContent(post: ImagePost, parent: ViewGroup): View =
    ViewContentImageBinding
        .inflate(inflater, parent, true)
        .apply { post.loadImageData(image, progress) }
        .root