package orwir.gazzit.listing.feed

import orwir.gazzit.listing.model.Submission
import orwir.gazzit.listing.util.imageUrlOrNull
import orwir.gazzit.listing.util.isImageUrl

interface PostImages {
    val preview: String
    val source: String
}

internal class RedditImages(submission: Submission) : PostImages {

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