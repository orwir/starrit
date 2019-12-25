package orwir.starrit.core.extension

import retrofit2.Retrofit

fun <T> service(retrofit: Retrofit, service: Class<T>): T = retrofit.create(service)