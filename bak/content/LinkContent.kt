package orwir.gazzit.listing.content

import android.net.Uri
import android.view.LayoutInflater
import androidx.browser.customtabs.CustomTabsIntent
import orwir.gazzit.databinding.ContentLinkBinding
import orwir.gazzit.model.Post

fun inflateLinkContent(post: Post, inflater: LayoutInflater) = ContentLinkBinding
    .inflate(inflater)
    .apply {
        this.post = post
        link.setOnClickListener {
            CustomTabsIntent
                .Builder()
                .build()
                .launchUrl(inflater.context, Uri.parse(post.url))
        }
    }
    .root