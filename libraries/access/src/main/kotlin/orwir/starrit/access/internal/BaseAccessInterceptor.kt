package orwir.starrit.access.internal

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.access.AccessInterceptor
import orwir.starrit.access.TokenException
import orwir.starrit.access.TokenException.ErrorCode
import orwir.starrit.core.BuildConfig.REDDIT_URL_OAUTH

internal class BaseAccessInterceptor : AccessInterceptor, KoinComponent {

    private val repository: TokenRepository by inject()

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
            response.close()
            throw TokenException("Access was revoked.", code = ErrorCode.ACCESS_DENIED)
        }
        return response
    }

    private fun Request.requireAuthorization() = url.toString().startsWith(REDDIT_URL_OAUTH)

}