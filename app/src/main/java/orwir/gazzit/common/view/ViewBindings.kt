package orwir.gazzit.common.view

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import orwir.gazzit.R

@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(show: Boolean) {
    visibility = if (show) VISIBLE else GONE
}

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String) = load(url) {
    placeholder(R.drawable.ic_image_placeholder)
}