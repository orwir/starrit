package orwir.starrit.authorization.internal

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.authorization.AccessInterceptor
import orwir.starrit.authorization.TokenException
import orwir.starrit.core.BuildConfig.REDDIT_URL_OAUTH
import orwir.starrit.core.event.EventBus

internal class BaseAccessInterceptor : AccessInterceptor, KoinComponent {

    private val repository: TokenRepository by inject()
    private val eventBus: EventBus.Medium by inject()

    @ExperimentalCoroutinesApi
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
        val response = chain.proceed(request)
        if (response.code == 401) {
            GlobalScope.launch {
                eventBus.send(TokenException("Token was revoked!", code = TokenException.ErrorCode.ACCESS_DENIED))
            }
        }
        return response
    }
    
    private fun Request.requireAuthorization() = url.toString().startsWith(REDDIT_URL_OAUTH)

}