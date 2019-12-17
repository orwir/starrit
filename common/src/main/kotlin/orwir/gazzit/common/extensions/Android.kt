package orwir.gazzit.common.extensions

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.koin.androidx.scope.currentScope

fun Context.launchActivity(clazz: Class<out AppCompatActivity>, builder: Intent.() -> Unit = {}) {
    startActivity(Intent(this, clazz).also(builder))
}

@Suppress("UNCHECKED_CAST")
fun <T> Fragment.arg(name: String): T = arguments!![name] as T

// todo: try to find more appropriate solution for scoped instances
inline fun <reified T> Fragment.injectFromActivityScope(): Lazy<T> =
    lazy { activity!!.currentScope.get<T>() }

// todo: check it
inline fun <reified T> View.injectFromActivityScope(): Lazy<T> =
    lazy { (context as AppCompatActivity).currentScope.get<T>() }