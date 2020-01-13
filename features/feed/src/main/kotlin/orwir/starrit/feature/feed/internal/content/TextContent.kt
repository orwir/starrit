package orwir.starrit.feature.feed.internal.content

import android.view.View
import android.view.ViewGroup
import orwir.starrit.feature.feed.databinding.ViewContentTextBinding
import orwir.starrit.listing.feed.TextPost

// todo: improve text content layout

@Suppress("FunctionName")
internal fun PostContentBinder.TextContent(post: TextPost, parent: ViewGroup): View =
    ViewContentTextBinding
        .inflate(inflater, parent, true)
        .also { it.post = post }
        .root