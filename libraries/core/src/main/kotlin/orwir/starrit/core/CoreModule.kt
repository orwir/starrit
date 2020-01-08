package orwir.starrit.core

import android.app.Application
import android.content.SharedPreferences
import coil.ImageLoader
import coil.decode.GifDecoder
import org.koin.dsl.module

val libCoreModule = module {

    single<SharedPreferences> {
        val app: Application = get()
        app.getSharedPreferences(app::class.qualifiedName, Application.MODE_PRIVATE)
    }

    single<ImageLoader> {
        ImageLoader(get()) {
            componentRegistry {
                add(GifDecoder())
            }
        }
    }

}