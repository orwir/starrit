package orwir.starrit

import okhttp3.Interceptor

internal class ApplicationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain) = chain
        .request()
        .newBuilder()
        .addHeader(
            "User-Agent",
            "android:${BuildConfig.APPLICATION_ID}:${BuildConfig.VERSION_NAME}"
        )
        .build()
        .let(chain::proceed)
}