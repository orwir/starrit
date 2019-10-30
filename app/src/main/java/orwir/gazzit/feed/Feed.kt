package orwir.gazzit.feed

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.service

val feedModule = module {

    single { service(get(), FeedService::class.java) }

    viewModel { FeedViewModel(get()) }

}