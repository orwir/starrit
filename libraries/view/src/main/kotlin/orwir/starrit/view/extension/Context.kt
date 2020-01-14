package orwir.starrit.view.extension

import android.content.Context
import android.content.ContextWrapper
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

fun Context.getColorFromAttr(@AttrRes attr: Int): Int {
    val outValue = TypedValue()
    theme.resolveAttribute(attr, outValue, true)
    return ContextCompat.getColor(this, outValue.resourceId)
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