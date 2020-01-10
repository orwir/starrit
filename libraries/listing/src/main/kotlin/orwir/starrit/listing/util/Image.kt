package orwir.starrit.listing.util

import orwir.starrit.listing.model.Submission

private val IMG_EXTENSIONS =
    arrayOf(
        "bmp",
        "jpg",
        "jpeg",
        "png",
        "svg",
        "tif",
        "tiff",
        "jfif",
        "pjpeg",
        "pjp",
        "ico",
        "cur"
    )

internal fun Submission.imageUrlOrNull(): String? = url.takeIf(::isImageUrl)

internal fun isImageUrl(url: String): Boolean = IMG_EXTENSIONS.contains(url.substring(url.lastIndexOf(".") + 1))