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

    fun setIcon(@DrawableRes iconId: Int): BannerBuilder {
        banner.setIcon(iconId)
        return this
    }

    fun setIcon(icon: Drawable?): BannerBuilder {
        banner.setIcon(icon)
        return this
    }

    fun setMessage(@StringRes messsageId: Int): BannerBuilder {
        banner.setMessage(messsageId)
        return this
    }

    fun setMessage(message: CharSequence?): BannerBuilder {
        banner.setMessage(message)
        return this
    }

    fun setPrimaryButton(@StringRes textId: Int, listener: OnBannerButtonClick): BannerBuilder {
        return setPrimaryButton(context.resources.getText(textId), listener)
    }

    fun setPrimaryButton(text: CharSequence, listener: OnBannerButtonClick): BannerBuilder {
        banner.setPrimaryText(text)
        banner.setPrimaryButtonListener(listener)
        return this
    }

    fun setSecondaryButton(@StringRes textId: Int, listener: OnBannerButtonClick): BannerBuilder {
        return setSecondaryButton(context.resources.getText(textId), listener)
    }

    fun setSecondaryButton(text: CharSequence, listener: OnBannerButtonClick): BannerBuilder {
        banner.setSecondaryText(text)
        banner.setSecondaryButtonListener(listener)
        return this
    }

    fun show(): Banner {
        parent?.addView(banner)
        return banner
    }

}