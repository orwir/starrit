package orwir.starrit.core.util

import android.content.res.Resources
import orwir.starrit.core.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun prettyDate(ms: Long, res: Resources): String {
    val diff = System.currentTimeMillis() - ms
    val diffInDays = TimeUnit.MILLISECONDS.toDays(diff).toInt()
    val diffInHours = TimeUnit.MILLISECONDS.toHours(diff).toInt()
    return when {
        diffInHours == 0 -> res.getString(R.string.df_now)
        diffInDays < 1 -> res.getString(R.string.df_today, diffInHours)
        diffInDays == 1 -> toDateString(
            ms,
            res.getString(R.string.df_yesterday)
        )
        isSameYear(ms) -> toDateString(
            ms,
            res.getString(R.string.df_this_year)
        )
        else -> toDateString(
            ms,
            res.getString(R.string.df_fulldate)
        )
    }
}

private fun toDateString(date: Long, format: String) =
    SimpleDateFormat(format, Locale.ENGLISH).format(date)

private fun isSameYear(ms: Long): Boolean {
    val now = Calendar.getInstance()
    val arg = Calendar.getInstance().apply { timeInMillis = ms }
    return now.get(Calendar.YEAR) == arg.get(Calendar.YEAR)
}