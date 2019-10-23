package orwir.gazzit.authorization.inner

import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Interceptor
import okhttp3.Response
import orwir.gazzit.REDDIT_AUTH_URL

@ExperimentalCoroutinesApi
class AuthorizationInterceptor(private val repository: AuthorizationRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.url.host == REDDIT_AUTH_URL) {
            val token = repository.validToken()
            request = request.newBuilder()
                .addHeader("Authorization", "${token.type} ${token.access}")
                .build()
        }
        return chain.proceed(request)
    }

}