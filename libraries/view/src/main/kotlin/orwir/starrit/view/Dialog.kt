package orwir.starrit.view

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.showErrorDialog(error: Exception, builder: MaterialAlertDialogBuilder.() -> Unit = {}): AlertDialog {
    val message =
        if (BuildConfig.DEBUG) error.toString() else getString(R.string.error_message_internal)
    return showErrorDialog(message, builder)
}

fun Fragment.showErrorDialog(message: String, builder: MaterialAlertDialogBuilder.() -> Unit = {}) =
    requireActivity().showDialog(getString(R.string.error), message, builder)

fun Context.showDialog(
    title: String,
    message: String,
    builder: MaterialAlertDialogBuilder.() -> Unit = {}
): AlertDialog = MaterialAlertDialogBuilder(this)
    .setTitle(title)
    .setMessage(message)
    .apply(builder)
    .show()