package orwir.starrit.listing.feed

import orwir.starrit.listing.model.Submission
import orwir.starrit.listing.util.imageUrlOrNull
import orwir.starrit.listing.util.isImageUrl

interface ImageData {
    val preview: String
    val source: String
    val blurred: String
    val width: Int
    val height: Int
}

@Suppress("FunctionName")
fun ImageData(submission: Submission): ImageData = object : ImageData {

    private val lowRes = submission.preview
        ?.images
        ?.firstOrNull()
        ?.resolutions
        ?.firstOrNull()

    private val highRes = submission.preview
        ?.images
        ?.firstOrNull()
        ?.source

    override val preview: String = lowRes
        ?.url
        ?: submission.thumbnail.takeIf(::isImageUrl)
        ?: ""

    override val source: String = highRes
        ?.url
        ?: submission.imageUrlOrNull()
        ?: ""

    override val blurred: String = submission
        .preview
        ?.images
        ?.firstOrNull()
        ?.variants
        ?.nsfw
        ?.resolutions
        ?.firstOrNull()
        ?.url
        ?: ""

    override val width: Int = lowRes?.width ?: highRes?.width ?: 0

    override val height: Int = lowRes?.height ?: highRes?.height ?: 0

}