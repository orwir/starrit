package orwir.starrit

import okhttp3.Interceptor

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
        .also {
            if (it.code == 401) {
//                val navigator: Navigator = DI.dependency()
//                val cause = TokenException("Authorization is revoked!", code = TokenException.ErrorCode.ACCESS_DENIED)
//                navigator.openAuthorization(cause)
            }
        }
}