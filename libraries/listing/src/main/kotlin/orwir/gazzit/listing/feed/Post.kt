package orwir.gazzit.listing.feed

import android.content.res.Resources
import android.net.Uri
import androidx.core.net.toUri
import orwir.gazzit.core.extensions.toHtml
import orwir.gazzit.core.util.createHashCode
import orwir.gazzit.core.util.prettyDate
import orwir.gazzit.core.util.squeeze
import orwir.gazzit.listing.R
import orwir.gazzit.listing.model.Submission
import orwir.gazzit.listing.model.Subreddit
import orwir.gazzit.listing.util.videoOrNull

sealed class Post(submission: Submission, res: Resources) {
    val id: String = submission.id
    val subreddit: Subreddit = submission.subreddit
    val author: String = submission.author
    val created: String = prettyDate(submission.created * 1000, res)
    val title: String = submission.title
    val nsfw: Boolean = submission.nsfw
    val spoiler: Boolean = submission.spoiler
    val comments: String = submission.commentsCount.squeeze()
    val score: String = prettyScore(submission, res)
    val domain: String = submission.domain
    val selfDomain: Boolean = domain.startsWith("self.")

    override fun equals(other: Any?) =
        other is Post
                && other::class == this::class
                && id == other.id
                && subreddit == other.subreddit
                && author == other.author
                && created == other.created
                && title == other.title
                && nsfw == other.nsfw
                && spoiler == other.spoiler
                && comments == other.comments
                && score == other.score
                && domain == other.domain

    override fun hashCode() = createHashCode(
        id,
        subreddit,
        author,
        created,
        title,
        nsfw,
        spoiler,
        comments,
        score,
        domain
    )

    private fun prettyScore(submission: Submission, res: Resources) = if (submission.hideScore) {
        res.getString(R.string.score_hidden)
    } else {
        submission.score.squeeze()
    }
}

class LinkPost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources), PostImages by RedditImages(submission) {
    val link: Uri = submission.url.toUri()
    val displayLink: String = link.authority ?: submission.url
}

class ImagePost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources), PostImages by RedditImages(
    submission
)

class GifPost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources), PostImages by RedditImages(submission) {
    val gif: String = submission.url
}

class TextPost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources) {
    val text: CharSequence = (submission.selftextHtml ?: submission.selftext ?: "").toHtml()
}

class VideoPost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources), PostImages by RedditImages(submission) {
    val video: String = submission.videoOrNull() ?: ""
}