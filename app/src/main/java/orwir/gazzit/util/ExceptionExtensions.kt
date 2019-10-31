package orwir.gazzit.util

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import orwir.gazzit.R
import retrofit2.HttpException

fun Fragment.handleException(ex: Exception) {
    ex.printStackTrace()
    when(ex) {
        is HttpException -> {
            if (ex.code() == 401) {
                findNavController().navigate(R.id.action_global_authorizationFragment)
            } else {
                Snackbar.make(view!!, ex.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
        else -> Snackbar.make(view!!, ex.toString(), Snackbar.LENGTH_LONG).show()
    }
}