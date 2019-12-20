package orwir.gazzit.feed

import orwir.gazzit.model.listing.Submission

internal fun Submission.videoOrNull() = when {
    isImgur() -> imgur()
    isReddit() -> reddit()
    isGfycat() -> gfycat()
    else -> null
}

private fun Submission.isImgur() = domain == "i.imgur.com" && url.endsWith(".gifv")

private fun Submission.isReddit() = domain == "v.redd.it"

private fun Submission.isGfycat() = domain == "gfycat.com"

private fun Submission.imgur() = url
    .replace("http://", "https://")
    .replace(".gifv", ".mp4")

private fun Submission.reddit() =
    secureMedia
        ?.reddit
        ?.hls
        ?: parents
            ?.firstOrNull()
            ?.secureMedia
            ?.reddit
            ?.hls

private fun Submission.gfycat() =
    secureMedia
        ?.oembed
        ?.thumbnail
        ?.replace("thumbs.gfycat.com", "giant.gfycat.com")
        ?.replace("-size_restricted", "")
        ?.replace(".gif", ".mp4")