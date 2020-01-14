package orwir.starrit.view.extension

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

@ColorInt
fun Context.getThemeColor(@AttrRes attr: Int): Int =
    obtainStyledAttributes(intArrayOf(attr)).run {
        try {
            getColor(0, Color.MAGENTA)
        } finally {
            recycle()
        }
    }

fun Context.getLifecycle(): Lifecycle? {
    var context = this
    while (true) {
        when (context) {
            is LifecycleOwner -> return context.lifecycle
            !is ContextWrapper -> return null
            else -> context = context.baseContext
        }
    }
}