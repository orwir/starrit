package orwir.starrit.core.extension

import android.os.Build
import android.text.Html

@Suppress("DEPRECATION")
fun String.toHtml(): CharSequence =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(this)
    } ?: ""