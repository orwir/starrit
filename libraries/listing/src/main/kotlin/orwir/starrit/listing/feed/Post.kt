package orwir.starrit.listing.feed

import android.content.res.Resources
import android.net.Uri
import androidx.core.net.toUri
import orwir.starrit.core.extension.toHtml
import orwir.starrit.core.util.createHashCode
import orwir.starrit.core.util.prettyDate
import orwir.starrit.core.util.squeeze
import orwir.starrit.listing.R
import orwir.starrit.listing.model.Image
import orwir.starrit.listing.model.Submission
import orwir.starrit.listing.model.Subreddit
import orwir.starrit.listing.util.videoOrNull
import java.io.Serializable

sealed class Post(submission: Submission, res: Resources) : Serializable {
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
    val contentUrl: String = submission.url
    val postUrl: String = submission.permalink
    val imagePreview: Image = submission.preview
        ?.images
        ?.firstOrNull()
        ?.resolutions
        ?.firstOrNull()
        ?: Image("", 0, 0)
    val imageSource: Image = submission.preview
        ?.images
        ?.firstOrNull()
        ?.source
        ?: Image("", 0, 0)
    val imageBlurred: Image = submission
        .preview
        ?.images
        ?.firstOrNull()
        ?.variants
        ?.nsfw
        ?.resolutions
        ?.firstOrNull()
        ?: Image("", 0, 0)

    override fun equals(other: Any?) = other is Post
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
            && imagePreview == other.imagePreview
            && imageSource == other.imageSource
            && imageBlurred == other.imageBlurred

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
        postUrl,
        imagePreview,
        imageSource,
        imageBlurred
    )

    private fun prettyScore(submission: Submission, res: Resources) =
        if (submission.hideScore) {
            res.getString(R.string.post_score_hidden)
        } else {
            submission.score.squeeze()
        }
}

class LinkPost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources) {
    val link: Uri = contentUrl.toUri()
    val displayLink: String = link.authority ?: submission.url
}

class ImagePost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources)

class GifPost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources) {
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
) : Post(submission, resources) {
    val video: String = submission.videoOrNull() ?: ""
}