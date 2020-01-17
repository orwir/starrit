package orwir.starrit.feature.feed.internal.content

import android.view.View
import android.view.ViewGroup
import orwir.starrit.feature.feed.databinding.ViewContentLinkBinding
import orwir.starrit.listing.feed.LinkPost

@Suppress("FunctionName")
internal fun PostContentBinder.LinkContent(post: LinkPost, parent: ViewGroup): View =
    ViewContentLinkBinding
        .inflate(inflater, parent, true)
        .apply {
            container.setOnClickListener { navigation.openBrowser(post.link) }
            text.text = post.displayLink
            post.loadImageData(image)
        }
        .root