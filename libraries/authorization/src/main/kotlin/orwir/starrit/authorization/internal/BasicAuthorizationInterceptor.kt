package orwir.starrit.authorization.internal

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import orwir.starrit.authorization.AuthorizationInterceptor
import orwir.starrit.core.BuildConfig.REDDIT_URL_OAUTH
import orwir.starrit.core.di.Injectable
import orwir.starrit.core.di.inject

internal class BasicAuthorizationInterceptor : AuthorizationInterceptor, Injectable {

    private val repository: TokenRepository by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.requireAuthorization()) {
            request = runBlocking {
                with(repository.obtainToken()) {
                    request.newBuilder()
                        .addHeader("Authorization", "$type $access")
                        .build()
                }
            }
        }
        return chain.proceed(request)
    }

    private fun Request.requireAuthorization() = url.toString().startsWith(REDDIT_URL_OAUTH)

}