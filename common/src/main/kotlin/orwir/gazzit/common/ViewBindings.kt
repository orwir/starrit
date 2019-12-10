package orwir.gazzit.common

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(show: Boolean = false) {
    visibility = if (show) VISIBLE else GONE
}