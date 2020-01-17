package orwir.starrit.view.extension

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import orwir.starrit.view.R

fun Context.showDialog(exception: Exception, config: MaterialAlertDialogBuilder.() -> Unit = {}): AlertDialog {
    return showDialog(getString(R.string.dialog_error_title), exception.displayMessage(this), config)
}

fun Context.showDialog(
    title: CharSequence,
    message: CharSequence,
    config: MaterialAlertDialogBuilder.() -> Unit = {}
): AlertDialog = MaterialAlertDialogBuilder(this)
    .setTitle(title)
    .setMessage(message)
    .apply(config)
    .show()