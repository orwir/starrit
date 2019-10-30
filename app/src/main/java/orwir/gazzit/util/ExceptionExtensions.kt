package orwir.gazzit.util

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import orwir.gazzit.R
import retrofit2.HttpException

fun Fragment.handleException(ex: Exception) {
    when(ex) {
        is HttpException -> {
            if (ex.code() == 401) {
                findNavController().navigate(R.id.action_global_authorizationFragment)
            }
        }
    }
}