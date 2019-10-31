package orwir.gazzit.feed

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.feed.source.FeedService
import orwir.gazzit.service

val feedModule = module {

    single { service(get(), FeedService::class.java) }

    single { FeedRepository() }

    viewModel { FeedViewModel(get()) }

}