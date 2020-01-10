package orwir.starrit.view.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(show: Boolean = false) {
    visibility = if (show) View.VISIBLE else View.GONE
}