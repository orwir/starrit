package orwir.gazzit.auth

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authRepository: AuthRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.url.host.startsWith("oauth")) {
            val token = authRepository.token.value!!
            request = request.newBuilder()
                .addHeader("Authorization", "${token.type} ${token.access}")
                .build()
        }
        return chain.proceed(request)
    }

}