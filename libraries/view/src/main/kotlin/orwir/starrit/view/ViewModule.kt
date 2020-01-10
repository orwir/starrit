package orwir.starrit.view

import coil.ImageLoader
import coil.decode.GifDecoder
import org.koin.dsl.module

val libraryViewModule = module {

    single {
        ImageLoader(get()) {
            componentRegistry {
                add(GifDecoder())
            }
        }
    }

}