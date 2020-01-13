package orwir.starrit.view

import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import org.koin.dsl.module

val libraryViewModule = module {

    single {
        ImageLoader(get()) {
            componentRegistry {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder())
                } else {
                    add(GifDecoder())
                }
            }
        }
    }

}