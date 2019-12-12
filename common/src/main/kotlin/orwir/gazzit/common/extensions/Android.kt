package orwir.gazzit.common.extensions

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Context.launchActivity(clazz: Class<out AppCompatActivity>, builder: Intent.() -> Unit = {}) {
    startActivity(Intent(this, clazz).also(builder))
}

fun <T> Fragment.arg(name: String): T = arguments!![name] as T