package orwir.starrit.core

import android.app.Application
import coil.ImageLoader
import coil.decode.GifDecoder
import org.koin.dsl.module

val libCoreModule = module {

    single {
        val app: Application = get()
        app.getSharedPreferences(app::class.qualifiedName, Application.MODE_PRIVATE)
    }

    single {
        ImageLoader(get()) {
            componentRegistry {
                add(GifDecoder())
            }
        }
    }

}