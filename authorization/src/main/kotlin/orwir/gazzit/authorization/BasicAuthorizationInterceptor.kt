package orwir.gazzit.authorization

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.gazzit.common.AuthorizationInterceptor
import orwir.gazzit.common.BuildConfig

internal class BasicAuthorizationInterceptor : AuthorizationInterceptor, KoinComponent {

    private val repository: AuthorizationRepository by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.url.toString().startsWith(BuildConfig.REDDIT_AUTH_URL)) {
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