package orwir.gazzit.feed.content

import android.content.Context
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.gazzit.feed.model.ImagePost
import orwir.gazzit.feed.model.LinkPost
import orwir.gazzit.feed.model.Post
import orwir.gazzit.model.listing.Submission

internal object PostResolver : KoinComponent {

    private val context: Context by inject()

    fun resolve(submission: Submission): Post = when {
        submission.imageUrlOrNull() != null -> ImagePost(submission, context.resources)
        else -> LinkPost(submission, context.resources)
    }

}