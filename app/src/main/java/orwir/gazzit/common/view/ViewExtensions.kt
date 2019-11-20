package orwir.gazzit.common.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

fun View.findRecyclerViewOrParent(): ViewGroup {
    var parentView: View? = this
    while (parentView != null) {
        val parent = parentView.parent as View?
        if (parent is RecyclerView) {
            return parent
        }
        parentView = parent
    }
    return this.parent as ViewGroup
}