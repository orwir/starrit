package orwir.starrit.feature.feed.internal.content

import android.view.View
import android.view.ViewGroup
import orwir.starrit.feature.feed.FeedNavigation
import orwir.starrit.feature.feed.databinding.ViewContentLinkBinding
import orwir.starrit.listing.feed.LinkPost

@Suppress("FunctionName")
internal fun PostContentBinder.LinkContent(post: LinkPost, parent: ViewGroup): View =
    ViewContentLinkBinding
        .inflate(inflater, parent, true)
        .apply { viewModel = LinkViewModel(post, navigation) }
        .root

class LinkViewModel(private val post: LinkPost, private val navigation: FeedNavigation) {
    val preview = post.preview
    val source = post.source
    val link = post.displayLink
    val openLink = View.OnClickListener { navigation.openBrowser(post.link) }
}