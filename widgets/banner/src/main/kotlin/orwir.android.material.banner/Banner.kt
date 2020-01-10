package orwir.android.material.banner

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.view_banner.view.*

class Banner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.bannerTextViewStyle,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), BannerInterface {

    private var primaryListener: OnBannerButtonClick? = null
    private var secondaryListener: OnBannerButtonClick? = null

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
                setIcon(getDrawable(R.styleable.Banner_icon))
                text.text = getText(R.styleable.Banner_text)
                button1.text = getText(R.styleable.Banner_primary_text)
                button2.text = getText(R.styleable.Banner_secondary_text)
            } finally {
                recycle()
            }
        }
        button1.setOnClickListener { primaryListener?.invoke(this) }
        button2.setOnClickListener { secondaryListener?.invoke(this) }
    }

    override fun dismiss() {
        val parent = this.parent
        if (parent is ViewGroup) {
            parent.removeView(this)
        } else {
            visibility = View.GONE
        }
    }

    fun setIcon(@DrawableRes iconId: Int) {
        setIcon(ContextCompat.getDrawable(context, iconId))
    }

    fun setIcon(icon: Drawable?) {
        this.icon.setImageDrawable(icon)
        this.icon.visibility = if (icon == null) View.GONE else View.VISIBLE
    }

    fun setMessage(@StringRes textId: Int) {
        setMessage(resources.getText(textId))
    }

    fun setMessage(text: CharSequence?) {
        this.text.text = text
        this.text.visibility = if (text?.isNotBlank() == true) View.VISIBLE else View.GONE
    }

    fun setPrimaryText(@StringRes textId: Int) {
        setPrimaryText(resources.getText(textId))
    }

    fun setPrimaryText(text: CharSequence) {
        button1.text = text
        updateButtonState(button1, primaryListener)
    }

    fun setSecondaryText(@StringRes textId: Int) {
        setSecondaryText(resources.getText(textId))
    }

    fun setSecondaryText(text: CharSequence) {
        button2.text = text
        updateButtonState(button2, secondaryListener)
    }

    fun setPrimaryButtonListener(listener: OnBannerButtonClick?) {
        primaryListener = listener
        updateButtonState(button1, listener)
    }

    fun setSecondaryButtonListener(listener: OnBannerButtonClick?) {
        secondaryListener = listener
        updateButtonState(button2, listener)
    }

    private fun updateButtonState(button: Button, listener: OnBannerButtonClick?) {
        button.visibility = if (listener == null || button1.text.isBlank()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

}