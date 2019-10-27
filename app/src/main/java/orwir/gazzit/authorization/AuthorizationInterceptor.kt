package orwir.gazzit.authorization

import okhttp3.Interceptor
import okhttp3.Response
import orwir.gazzit.REDDIT_AUTH_URL

class AuthorizationInterceptor(private val repository: AuthorizationRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.url.host == REDDIT_AUTH_URL) {
            require(repository.hasToken()) { "No token found!" }
            val token = repository.obtainToken()
            request = request.newBuilder()
                .addHeader("Authorization", "${token.type} ${token.access}")
                .build()
        }
        return chain.proceed(request)
    }

}