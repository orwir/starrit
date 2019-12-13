package orwir.gazzit.common.extensions

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.koin.androidx.scope.currentScope

fun Context.launchActivity(clazz: Class<out AppCompatActivity>, builder: Intent.() -> Unit = {}) {
    startActivity(Intent(this, clazz).also(builder))
}

@Suppress("UNCHECKED_CAST")
fun <T> Fragment.arg(name: String): T = arguments!![name] as T

inline fun <reified T> Fragment.injectFromActivityScope(): Lazy<T> =
    lazy { activity!!.currentScope.get<T>() }