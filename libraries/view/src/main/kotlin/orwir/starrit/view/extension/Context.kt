package orwir.starrit.view.extension

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat

fun Context.getColorFromAttr(@AttrRes attr: Int): Int {
    val outValue = TypedValue()
    theme.resolveAttribute(attr, outValue, true)
    return ContextCompat.getColor(this, outValue.resourceId)
}