package orwir.gazzit.feed.model

import orwir.gazzit.feed.imageUrlOrNull
import orwir.gazzit.feed.isImageUrl
import orwir.gazzit.model.listing.Submission

internal interface ImagesData {
    val preview: String
    val source: String
}

internal class RedditImageData(submission: Submission) : ImagesData {

    override val preview: String = submission.preview
        ?.images
        ?.firstOrNull()
        ?.resolutions
        ?.firstOrNull()
        ?.url
        ?: submission.thumbnail.takeIf(::isImageUrl)
        ?: ""

    override val source: String = submission.preview
        ?.images
        ?.firstOrNull()
        ?.source
        ?.url
        ?: submission.imageUrlOrNull()
        ?: ""

}