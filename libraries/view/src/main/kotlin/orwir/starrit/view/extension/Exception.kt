package orwir.starrit.view.extension

import android.content.Context
import orwir.starrit.view.BuildConfig
import orwir.starrit.view.R

fun Exception.displayMessage(context: Context) =
    if (BuildConfig.DEBUG) toString() else context.getString(R.string.error_message_stub)