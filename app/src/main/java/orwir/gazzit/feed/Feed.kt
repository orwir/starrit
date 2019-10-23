package orwir.gazzit.feed

import org.koin.dsl.module

val feedModule = module {
    single<FeedRepository> { BaseFeedRepository(get()) }
}

interface FeedRepository {

    fun best()

}