package orwir.android.material.banner

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class BannerBuilder(private val context: Context) {

    private val banner: Banner = Banner(context)
    private var parent: ViewGroup? = null

    fun setParent(parent: ViewGroup): BannerBuilder {
        this.parent = parent
        return this
    }

    fun setIcon(@DrawableRes iconId: Int) = apply {
        banner.setIcon(iconId)
    }

    fun setIcon(icon: Drawable?) = apply {
        banner.setIcon(icon)
    }

    fun setMessage(@StringRes messageId: Int) = apply {
        banner.setMessage(messageId)
    }

    fun setMessage(message: CharSequence?) = apply {
        banner.setMessage(message)
    }

    fun setPrimaryButton(@StringRes textId: Int, listener: OnBannerButtonClick) = apply {
        setPrimaryButton(context.resources.getText(textId), listener)
    }

    fun setPrimaryButton(text: CharSequence, listener: OnBannerButtonClick) = apply {
        banner.setPrimaryText(text)
        banner.setPrimaryButtonListener(listener)
    }

    fun setSecondaryButton(@StringRes textId: Int, listener: OnBannerButtonClick) = apply {
        setSecondaryButton(context.resources.getText(textId), listener)
    }

    fun setSecondaryButton(text: CharSequence, listener: OnBannerButtonClick) = apply {
        banner.setSecondaryText(text)
        banner.setSecondaryButtonListener(listener)
    }

    fun show(): Banner {
        parent?.addView(banner)
        return banner
    }

}