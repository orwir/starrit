package orwir.starrit.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textview.MaterialTextView

class GhostTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.ghostTextViewStyle
) : MaterialTextView(context, attrs, defStyleAttr) {

    private val transparent = ContextCompat.getColor(context, android.R.color.transparent)
    private var ghostColor: Int = transparent

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.GhostTextView, defStyleAttr, 0)
            .apply {
                try {
                    ghostColor = getColor(R.styleable.GhostTextView_colorGhost, transparent)
                } finally {
                    recycle()
                }
            }

        doAfterTextChanged {
            updateGhostEffect(it == null)
        }

        updateGhostEffect(true)
    }

    private fun updateGhostEffect(show: Boolean) {
        setBackgroundColor(if (show) ghostColor else transparent)
    }

}