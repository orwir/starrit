package orwir.starrit.content

import android.app.Application
import org.koin.dsl.module
import orwir.starrit.content.feed.FeedRepository
import orwir.starrit.content.internal.feed.BaseFeedRepository
import orwir.starrit.content.internal.feed.ListingService
import orwir.starrit.content.internal.post.PostResolver
import orwir.starrit.core.extension.service

val libContentModule = module {

    single<FeedRepository> { BaseFeedRepository() }

    single { service(get(), ListingService::class.java) }

    single { PostResolver(get<Application>().resources) }

}