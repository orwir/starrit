package orwir.starrit.authorization.internal

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.authorization.AccessInterceptor
import orwir.starrit.core.BuildConfig.REDDIT_URL_OAUTH

internal class BaseAccessInterceptor : AccessInterceptor, KoinComponent {

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
        // todo: check authorization exceptions here
        return chain.proceed(request)
    }

    private fun Request.requireAuthorization() = url.toString().startsWith(REDDIT_URL_OAUTH)

}