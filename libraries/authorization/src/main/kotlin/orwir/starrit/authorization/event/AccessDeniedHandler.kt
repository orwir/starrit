package orwir.starrit.authorization.event

import android.content.Context
import orwir.starrit.authorization.R
import orwir.starrit.view.event.EventHandler
import orwir.starrit.view.event.showErrorDialog

class AccessDeniedHandler : EventHandler.Dialog {

    override fun handle(context: Context) {
        context.showErrorDialog("") {
            setTitle(R.string.dialog_access_denied_title)
            setMessage(R.string.dialog_access_denied_message)
            setPositiveButton(R.string.dialog_access_denied_positive_action) { _, _ ->
                TODO("start authorization flow and open reddit.com to allow access")
            }
        }
    }

}