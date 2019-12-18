package orwir.gazzit.common

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit

interface AuthorizationInterceptor : Interceptor

fun <T> service(retrofit: Retrofit, service: Class<T>): T = retrofit.create(service)

class RedditInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain
            .request()
            .newBuilder()
            .addHeader(
                "User-Agent",
                "android:orwir.gazzit:${BuildConfig.VERSION_NAME}"
            )
            .build()
            .let(chain::proceed)
    }

}