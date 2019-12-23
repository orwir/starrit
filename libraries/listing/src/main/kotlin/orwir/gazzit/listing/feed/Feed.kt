package orwir.gazzit.listing.feed

data class Feed(
    val posts: List<Post>,
    val before: String?,
    val after: String?
)