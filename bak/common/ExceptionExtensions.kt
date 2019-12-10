package orwir.gazzit.common

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException

fun Fragment.handleException(ex: Exception) {
    ex.printStackTrace()
    when {
        ex is HttpException && ex.code() == 401 && activity is AuthorizationHolder -> {
            (activity as AuthorizationHolder).request()
        }
        else -> Snackbar.make(view!!, ex.toString(), Snackbar.LENGTH_LONG).show()
    }
}