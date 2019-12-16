package orwir.gazzit.feed.content

import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.feed.databinding.ViewContentLinkBinding
import orwir.gazzit.model.Post

internal fun inflateLinkContent(post: Post, inflater: LayoutInflater): View =
    ViewContentLinkBinding
        .inflate(inflater)
        .apply {

        }
        .root

/*
    val previewThumb = preview
        ?.images
        ?.get(0)
        ?.resolutions
        ?.get(0)
        ?.url
        ?.replace("&amp;", "&")
        ?: thumbnail

    val previewSource = preview
        ?.images
        ?.get(0)
        ?.source
        ?.url
        ?.replace("&amp;", "&")
        ?: url
 */