package orwir.gazzit.feed.util

import orwir.gazzit.model.listing.Submission

internal fun Submission.imageUrlOrNull(): String? = when {
    isImageUrl(url) -> url
    domain == "imgur.com" -> "$url.png"
    else -> null
}

internal fun isImageUrl(url: String) = url.endsWith(".png") || url.endsWith(".jpg")