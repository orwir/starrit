package orwir.gazzit.feed

import android.content.Context
import orwir.gazzit.feed.model.GifPost
import orwir.gazzit.feed.model.ImagePost
import orwir.gazzit.feed.model.LinkPost
import orwir.gazzit.feed.model.Post
import orwir.gazzit.model.listing.Submission

internal class PostResolver(private val context: Context) {
    fun resolve(submission: Submission): Post = when {
        submission.imageUrlOrNull() != null -> ImagePost(submission, context.resources)
        submission.url.endsWith(".gif") -> GifPost(submission, context.resources)
        else -> LinkPost(submission, context.resources)
    }
}