package orwir.starrit.feature.feed.internal.content

import android.view.View
import orwir.starrit.feature.feed.databinding.ViewContentLinkBinding
import orwir.starrit.listing.feed.LinkPost

// todo: improve link content layout

@Suppress("FunctionName")
internal fun PostContentBinder.LinkContent(post: LinkPost): View =
    ViewContentLinkBinding
        .inflate(inflater)
        .also {
            it.post = post
            it.listener = View.OnClickListener { navigation.openBrowser(post.link) }
        }
        .root