package orwir.gazzit.listing.feed

import android.content.Context
import orwir.gazzit.listing.model.Submission
import orwir.gazzit.listing.util.imageUrlOrNull
import orwir.gazzit.listing.util.videoOrNull

internal class PostResolver(private val context: Context) {
    fun resolve(submission: Submission): Post = submission.run {
        val resources = context.resources
        when {
            hasText() -> TextPost(submission, resources)
            hasGif() -> GifPost(submission, resources)
            hasImage() -> ImagePost(submission, resources)
            hasVideo() -> VideoPost(submission, resources)
            else -> LinkPost(submission, resources)
        }
    }
}

private fun Submission.hasText() =
    selftextHtml?.isNotBlank() == true || selftext?.isNotBlank() == true

private fun Submission.hasImage() = imageUrlOrNull() != null

private fun Submission.hasGif() = url.endsWith(".gif")

private fun Submission.hasVideo() = videoOrNull() != null