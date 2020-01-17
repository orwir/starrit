package orwir.starrit.authorization

import android.content.Context
import android.widget.FrameLayout
import orwir.starrit.view.extension.showBanner
import orwir.starrit.view.extension.showDialog

fun showAccessDenied(context: Context, action: () -> Unit) {
    context.showDialog("", "") {
        setTitle(R.string.dialog_access_denied_title)
        setMessage(R.string.dialog_access_denied_message)
        setPositiveButton(R.string.dialog_access_denied_positive_action) { _, _ -> action() }
    }
}

fun showAccessRevoked(container: FrameLayout, action: () -> Unit) {
    container.showBanner("") {
        setIcon(R.drawable.ic_account)
        setMessage(R.string.banner_access_revoked_message)
        setPrimaryButton(R.string.banner_access_revoked_primary_action) { action() }
    }
}

fun showAccessProposal(container: FrameLayout) {
    container.showBanner("") {
        setIcon(R.drawable.ic_account)
        setMessage(R.string.banner_access_proposal_message)
        setPrimaryButton(R.string.banner_access_proposal_primary_action) {
            TODO("start authorization flow and open reddit.com to allow access")
        }
        setSecondaryButton(R.string.banner_access_proposal_secondary_action) {
            dismiss()
            TODO("set anonymous access")
        }
    }
}