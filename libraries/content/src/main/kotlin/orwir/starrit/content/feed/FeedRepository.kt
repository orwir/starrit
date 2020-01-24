package orwir.starrit.content.feed

import kotlinx.coroutines.CoroutineScope

interface FeedRepository {

    fun feed(type: Feed.Type, sort: Feed.Sort, scope: CoroutineScope): Feed

    suspend fun searchSubreddits(name: String): List<String>

}