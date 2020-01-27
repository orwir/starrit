package orwir.starrit.content

import android.app.Application
import org.koin.dsl.bind
import org.koin.dsl.module
import orwir.starrit.content.feed.FeedPreferences
import orwir.starrit.content.feed.FeedRepository
import orwir.starrit.content.internal.feed.BaseFeedRepository
import orwir.starrit.content.internal.feed.ListingService
import orwir.starrit.content.internal.post.PostResolver
import orwir.starrit.content.post.PostPreferences
import orwir.starrit.core.extension.service

val libraryContentModule = module {

    single { FeedPreferences() } bind PostPreferences::class

    single<FeedRepository> { BaseFeedRepository() }

    single { service(get(), ListingService::class.java) }

    single { PostResolver(get<Application>().resources) }

}