package orwir.starrit.view

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(context: Context, @DimenRes size: Int) : RecyclerView.ItemDecoration() {

    private val marginPx: Int = context.resources.getDimension(size).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.apply {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = marginPx
            }
            left = marginPx
            right = marginPx
            bottom = marginPx
        }
    }

}