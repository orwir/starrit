package orwir.starrit.view.extension

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import orwir.starrit.view.BuildConfig
import orwir.starrit.view.R

fun Context.showErrorDialog(error: Exception, builder: MaterialAlertDialogBuilder.() -> Unit = {}): AlertDialog {
    val message = if (BuildConfig.DEBUG) error.toString() else getString(R.string.dialog_error_message_stub)
    return showErrorDialog(message, builder)
}

fun Context.showErrorDialog(message: String, builder: MaterialAlertDialogBuilder.() -> Unit = {}) =
    showDialog(getString(R.string.dialog_error_title), message, builder)

fun Context.showDialog(
    title: String,
    message: String,
    builder: MaterialAlertDialogBuilder.() -> Unit = {}
): AlertDialog = MaterialAlertDialogBuilder(this)
    .setTitle(title)
    .setMessage(message)
    .apply(builder)
    .show()