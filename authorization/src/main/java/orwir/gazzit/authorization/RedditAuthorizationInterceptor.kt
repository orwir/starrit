package orwir.gazzit.authorization

import okhttp3.Interceptor
import okhttp3.Response
import orwir.gazzit.common.REDDIT_AUTH_URL
import orwir.gazzit.common.AuthorizationInterceptor

class RedditAuthorizationInterceptor(private val holder: TokenHolder) : AuthorizationInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.url.host == REDDIT_AUTH_URL) {
            val token = holder()
            request = request.newBuilder()
                .addHeader("Authorization", "${token.type} ${token.access}")
                .build()
        }
        return chain.proceed(request)
    }

}