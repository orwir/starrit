package orwir.starrit.view.extension

import android.widget.FrameLayout
import orwir.android.material.banner.Banner
import orwir.android.material.banner.BannerBuilder

fun FrameLayout.showBanner(message: CharSequence, config: BannerBuilder.() -> Unit): Banner {
    removeAllViews()
    return BannerBuilder(context)
        .setParent(this)
        .setMessage(message)
        .apply(config)
        .show()
}