package orwir.gazzit.feed

import org.koin.dsl.module
import orwir.gazzit.common.service

val feedModule = module {

    single<FeedService> {
        service(get(), FeedService::class.java)
    }

}

internal const val FEED_ITEMS_LIMIT = 15