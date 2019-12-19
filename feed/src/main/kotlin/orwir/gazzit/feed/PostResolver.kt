package orwir.gazzit.feed

import android.content.Context
import orwir.gazzit.feed.model.*
import orwir.gazzit.model.listing.Submission

internal class PostResolver(private val context: Context) {
    fun resolve(submission: Submission): Post = submission.run {
        val resources = context.resources
        when {
            hasText() -> TextPost(submission, resources)
            hasGif() -> GifPost(submission, resources)
            hasImage() -> ImagePost(submission, resources)
            else -> LinkPost(submission, resources)
        }
    }
}

private fun Submission.hasText() =
    selftextHtml?.isNotBlank() == true || selftext?.isNotBlank() == true

private fun Submission.hasImage() = imageUrlOrNull() != null

private fun Submission.hasGif() = url.endsWith(".gif")