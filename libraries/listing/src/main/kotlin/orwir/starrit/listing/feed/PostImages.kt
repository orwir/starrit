package orwir.starrit.listing.feed

import orwir.starrit.listing.model.Submission
import orwir.starrit.listing.util.imageUrlOrNull
import orwir.starrit.listing.util.isImageUrl

interface PostImages {
    val preview: String
    val source: String
}

internal class RedditImages(submission: Submission) :
    PostImages {

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