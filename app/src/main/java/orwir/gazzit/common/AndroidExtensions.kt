package orwir.gazzit.common

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun Context.launchActivity(clazz: Class<out AppCompatActivity>, builder: Intent.() -> Unit = {}) {
    startActivity(Intent(this, clazz).also(builder))
}