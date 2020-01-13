package orwir.starrit.feature.feed.internal.content

import android.view.View
import android.view.ViewGroup
import orwir.starrit.feature.feed.databinding.ViewContentLinkBinding
import orwir.starrit.listing.feed.LinkPost

// todo: improve link content layout

@Suppress("FunctionName")
internal fun PostContentBinder.LinkContent(post: LinkPost, parent: ViewGroup): View =
    ViewContentLinkBinding
        .inflate(inflater, parent, true)
        .also {
            it.post = post
            it.listener = View.OnClickListener { navigation.openBrowser(post.link) }
        }
        .root