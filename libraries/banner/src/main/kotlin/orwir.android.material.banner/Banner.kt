package orwir.android.material.banner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_banner.view.*

class Banner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.bannerTextViewStyle,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater
            .from(context)
            .inflate(R.layout.view_banner, this, true)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Banner,
            defStyleAttr,
            defStyleRes
        ).apply {
            try {
                icon.setImageDrawable(getDrawable(R.styleable.Banner_icon))
                text.text = getText(R.styleable.Banner_text)
                button1.text = getText(R.styleable.Banner_primary_text)
                button2.text = getText(R.styleable.Banner_secondary_text)
            } finally {
                recycle()
            }
        }
    }

}