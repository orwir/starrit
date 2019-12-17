package orwir.gazzit.feed.content

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import orwir.gazzit.feed.databinding.ViewContentTextBinding
import orwir.gazzit.model.Post

internal fun isText(post: Post): Boolean = post.text?.isNotBlank() ?: false

internal fun inflateTextContent(post: Post, inflater: LayoutInflater): View =
    ViewContentTextBinding
        .inflate(inflater)
        .apply {
            text = post.text?.toHtml() ?: ""
        }
        .root

private fun String.toHtml() =
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        Html.fromHtml(this)
    } else {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    }