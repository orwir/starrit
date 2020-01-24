package orwir.starrit.content.internal.post

import android.view.View
import android.view.ViewGroup
import orwir.starrit.content.databinding.ViewContentLinkBinding
import orwir.starrit.content.post.LinkPost

@Suppress("FunctionName")
internal fun PostContentBinder.LinkContent(post: LinkPost, parent: ViewGroup): View =
    ViewContentLinkBinding
        .inflate(inflater, parent, true)
        .apply {
            veil.setOnClickListener { navigation.openBrowser(post.link) }
            text.text = post.displayLink
            image.loadImageData(post)
        }
        .root