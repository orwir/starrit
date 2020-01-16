package orwir.starrit.view.extension

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
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

val Context.lifecycleOwner: LifecycleOwner?
    get() {
        var context = this
        while (true) {
            when (context) {
                is LifecycleOwner -> return context
                !is ContextWrapper -> return null
                else -> context = context.baseContext
            }
        }
    }