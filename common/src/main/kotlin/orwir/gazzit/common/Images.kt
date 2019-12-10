package orwir.gazzit.common

import android.content.Context
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.util.CoilUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun initializeCoil(context: Context) {
    Coil.setDefaultImageLoader {
        ImageLoader(context) {
            okHttpClient {
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .cache(CoilUtils.createDefaultCache(context))
                    .build()
            }
            componentRegistry {
                add(GifDecoder())
            }
        }
    }
}