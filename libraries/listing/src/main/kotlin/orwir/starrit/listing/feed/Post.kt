package orwir.starrit.listing.feed

import android.content.res.Resources
import android.net.Uri
import androidx.core.net.toUri
import orwir.starrit.core.extension.toHtml
import orwir.starrit.core.util.createHashCode
import orwir.starrit.core.util.prettyDate
import orwir.starrit.core.util.squeeze
import orwir.starrit.listing.R
import orwir.starrit.listing.model.Submission
import orwir.starrit.listing.model.Subreddit
import orwir.starrit.listing.util.videoOrNull

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
    val contentUrl = submission.url
    val postUrl = submission.permalink

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
                && contentUrl == other.contentUrl
                && postUrl == other.postUrl

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
        domain,
        contentUrl,
        postUrl
    )

    private fun prettyScore(submission: Submission, res: Resources) =
        if (submission.hideScore) {
            res.getString(R.string.score_hidden)
        } else {
            submission.score.squeeze()
        }
}

class LinkPost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources), ImageData by ImageData(submission) {
    val link: Uri = contentUrl.toUri()
    val displayLink: String = link.authority ?: submission.url
}

class ImagePost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources), ImageData by ImageData(submission)

class GifPost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources), ImageData by ImageData(submission) {
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
) : Post(submission, resources), ImageData by ImageData(submission) {
    val video: String = submission.videoOrNull() ?: ""
}