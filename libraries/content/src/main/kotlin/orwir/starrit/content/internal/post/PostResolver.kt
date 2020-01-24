package orwir.starrit.content.internal.post

import android.content.res.Resources
import orwir.starrit.content.internal.util.imageUrlOrNull
import orwir.starrit.content.internal.util.videoOrNull
import orwir.starrit.content.model.Submission
import orwir.starrit.content.post.*

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