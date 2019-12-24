package orwir.starrit.network

import okhttp3.Interceptor
import orwir.starrit.BuildConfig

internal class CoreInterceptor : Interceptor {
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