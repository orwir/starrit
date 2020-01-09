package orwir.starrit.network

import okhttp3.Interceptor
import org.koin.core.KoinComponent
import org.koin.core.get
import orwir.starrit.BuildConfig
import orwir.starrit.Navigator

internal class CoreInterceptor : Interceptor, KoinComponent {
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
                val navigator: Navigator = get()
                navigator.openAuthorization()
            }
        }
}