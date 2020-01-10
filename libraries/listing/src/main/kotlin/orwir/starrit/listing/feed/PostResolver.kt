package orwir.starrit.listing.feed

import android.content.res.Resources
import orwir.starrit.listing.model.Submission
import orwir.starrit.listing.util.imageUrlOrNull
import orwir.starrit.listing.util.videoOrNull

internal class PostResolver(private val resources: Resources) {

    fun resolve(submission: Submission): Post = submission.run {
        when {
            hasGif() -> GifPost(submission, resources)
            hasImage() -> ImagePost(submission, resources)
            hasVideo() -> VideoPost(submission, resources)
            hasText() -> TextPost(submission, resources)
            else -> LinkPost(submission, resources)
        }
    }

    private fun Submission.hasText() =
        selftextHtml?.isNotBlank() == true || selftext?.isNotBlank() == true || domain.startsWith("self.")

    private fun Submission.hasImage() = imageUrlOrNull() != null

    private fun Submission.hasGif() = url.endsWith(".gif")

    private fun Submission.hasVideo() = videoOrNull() != null

}