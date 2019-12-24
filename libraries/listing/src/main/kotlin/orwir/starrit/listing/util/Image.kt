package orwir.starrit.listing.util

import orwir.starrit.listing.model.Submission

internal fun Submission.imageUrlOrNull(): String? = when {
    isImageUrl(url) -> url
    domain == "imgur.com" -> "$url.png"
    else -> null
}

internal fun isImageUrl(url: String) = url.endsWith(".png") || url.endsWith(".jpg")