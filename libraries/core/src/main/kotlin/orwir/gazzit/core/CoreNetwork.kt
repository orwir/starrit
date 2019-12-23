package orwir.gazzit.core

import okhttp3.Interceptor
import retrofit2.Retrofit

interface AuthorizationInterceptor : Interceptor

fun <T> service(retrofit: Retrofit, service: Class<T>): T = retrofit.create(service)