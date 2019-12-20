package orwir.gazzit.feed.model

import orwir.gazzit.feed.util.imageUrlOrNull
import orwir.gazzit.feed.util.isImageUrl
import orwir.gazzit.model.listing.Submission

internal interface Images {
    val preview: String
    val source: String
}

internal class RedditImages(submission: Submission) : Images {

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