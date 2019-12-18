package orwir.gazzit.common.view

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

private fun findActivity(context: Context): AppCompatActivity =
    when (context) {
        is ContextWrapper -> findActivity(context.baseContext)
        is AppCompatActivity -> context
        else -> throw IllegalStateException()
    }

fun View.activity(): AppCompatActivity = findActivity(context)