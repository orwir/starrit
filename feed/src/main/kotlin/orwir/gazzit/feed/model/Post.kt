package orwir.gazzit.feed.model

import android.content.res.Resources
import orwir.gazzit.common.extensions.squeeze
import orwir.gazzit.feed.R
import orwir.gazzit.model.listing.Submission
import orwir.gazzit.model.listing.Subreddit

internal sealed class Post(submission: Submission, res: Resources) {
    val id: String = submission.id
    val subreddit: Subreddit = submission.subreddit
    val author: String = submission.author
    val created: String = submission.created.toString() // todo: make it human readable
    val title: String = submission.title
    val nsfw: Boolean = submission.nsfw
    val spoiler: Boolean = submission.spoiler
    val comments: String = submission.commentsCount.squeeze()
    val score: String =
        if (submission.hideScore) res.getString(R.string.score_hidden) else submission.score.squeeze()
    val domain: String = submission.domain
}

internal class LinkPost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources), ImagesData by RedditImageData(submission) {
    val link: String = submission.url
}

internal class ImagePost(
    submission: Submission,
    resources: Resources
) : Post(submission, resources), ImagesData by RedditImageData(submission)