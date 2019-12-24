package orwir.starrit.network

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

internal class NetworkLoggingInterceptor(
    level: HttpLoggingInterceptor.Level,
    logger: HttpLoggingInterceptor = HttpLoggingInterceptor().also { it.level = level }
) : Interceptor by logger