package orwir.starrit.view.extension

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar.make

fun CoordinatorLayout.showSnackbar(exception: Exception, config: Snackbar.() -> Unit = {}) {
    showSnackbar(exception.displayMessage(context), config)
}

fun CoordinatorLayout.showSnackbar(message: CharSequence, config: Snackbar.() -> Unit = {}) {
    make(this, message, LENGTH_LONG)
        .apply(config)
        .show()
}