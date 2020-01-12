package orwir.starrit.feature.feed.internal.content

import android.view.View
import orwir.starrit.feature.feed.databinding.ViewContentTextBinding
import orwir.starrit.listing.feed.TextPost

// todo: improve text content layout

@Suppress("FunctionName")
internal fun PostContentBinder.TextContent(post: TextPost): View =
    ViewContentTextBinding
        .inflate(inflater)
        .also { it.post = post }
        .root