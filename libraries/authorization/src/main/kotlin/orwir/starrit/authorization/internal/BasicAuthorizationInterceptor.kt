package orwir.starrit.authorization.internal

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.authorization.AuthorizationInterceptor
import orwir.starrit.core.BuildConfig.REDDIT_URL_OAUTH

internal class BasicAuthorizationInterceptor : AuthorizationInterceptor, KoinComponent {

    private val repository: TokenRepository by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.url.toString().startsWith(REDDIT_URL_OAUTH)) {
            runBlocking {
                val token = repository.obtainToken()
                request = request.newBuilder()
                    .addHeader("Authorization", "${token.type} ${token.access}")
                    .build()
            }
        }
        return chain.proceed(request)
    }

}