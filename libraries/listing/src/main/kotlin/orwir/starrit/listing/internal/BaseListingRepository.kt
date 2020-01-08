package orwir.starrit.listing.internal

import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.authorization.AccessRepository
import orwir.starrit.authorization.model.AccessType
import orwir.starrit.core.BuildConfig.REDDIT_URL_BASIC
import orwir.starrit.core.BuildConfig.REDDIT_URL_OAUTH
import orwir.starrit.listing.ListingRepository
import orwir.starrit.listing.feed.Feed
import orwir.starrit.listing.feed.PostResolver

internal class BaseListingRepository : ListingRepository, KoinComponent {

    private val service: ListingService by inject()
    private val resolver: PostResolver by inject()
    private val accessRepository: AccessRepository by inject()

    override suspend fun feed(type: Feed.Type, sort: Feed.Sort, before: String?, after: String?, limit: Int?): Feed =
        service.listing(
            base = if (accessRepository.accessType() == AccessType.AUTHORIZED) REDDIT_URL_OAUTH else REDDIT_URL_BASIC,
            subreddit = type.asParameter(),
            sort = sort.asParameter(),
            before = before,
            after = after,
            limit = limit
        ).run {
            Feed(
                type = type,
                sort = sort,
                posts = data.children.map { resolver.resolve(it.data) },
                before = data.before,
                after = data.after
            )
        }

}