package orwir.starrit.authorization.event

import android.widget.FrameLayout
import orwir.android.material.banner.BannerBuilder
import orwir.starrit.authorization.R
import orwir.starrit.view.event.EventHandler

class AccessRevokedHandler : EventHandler.Banner {

    override fun handle(container: FrameLayout) {
        BannerBuilder(container.context)
            .setParent(container)
            .setIcon(R.drawable.ic_account)
            .setMessage(R.string.banner_access_revoked_message)
            .setPrimaryButton(R.string.banner_access_revoked_primary_action) {
                TODO("start authorization flow and open reddit.com to allow access")
            }
            .show()
    }

}