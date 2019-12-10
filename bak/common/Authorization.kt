package orwir.gazzit.common

import okhttp3.Interceptor

interface AuthorizationHolder {
    fun request()
}

interface AuthorizationInterceptor : Interceptor