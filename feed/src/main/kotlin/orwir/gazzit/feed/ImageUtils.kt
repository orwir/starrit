package orwir.gazzit.feed

import orwir.gazzit.model.listing.Submission

internal fun isImageUrl(url: String) = url.endsWith(".png") || url.endsWith(".jpg")

internal fun Submission.imageUrlOrNull(): String? = when {
    isImageUrl(url) -> url
    domain == "imgur.com" -> "$url.png"
    else -> null
}