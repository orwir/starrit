package orwir.gazzit

import okhttp3.Interceptor
import okhttp3.Response
import orwir.gazzit.common.HeadersInterceptor

class RedditHeadersInterceptor : HeadersInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain
        .request()
        .newBuilder()
        .addHeader(
            "User-Agent",
            "android:${BuildConfig.APPLICATION_ID}:${BuildConfig.VERSION_NAME}"
        )
        .build()
        .let(chain::proceed)
}