package orwir.starrit.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spacePx: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.apply {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spacePx
            }
            left = spacePx
            right = spacePx
            bottom = spacePx
        }
    }

}