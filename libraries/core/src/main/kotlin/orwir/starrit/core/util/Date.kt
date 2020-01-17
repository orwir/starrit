package orwir.starrit.core.util

import android.content.res.Resources
import orwir.starrit.core.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun prettyDate(ms: Long, res: Resources): String {
    val diff = System.currentTimeMillis() - ms
    return when {
        isLessThanHour(diff) -> res.getString(R.string.pretty_date_now)
        isLessThanDay(diff) -> res.getString(R.string.pretty_date_today, TimeUnit.MILLISECONDS.toHours(diff))
        isYesterday(diff) -> toDateString(ms, res.getString(R.string.pretty_date_yesterday))
        isSameYear(ms) -> toDateString(ms, res.getString(R.string.pretty_date_this_year))
        else -> toDateString(ms, res.getString(R.string.pretty_date_fulldate))
    }
}

private fun toDateString(date: Long, format: String) = SimpleDateFormat(format, Locale.ENGLISH).format(date)

private fun isLessThanHour(diff: Long): Boolean = TimeUnit.MILLISECONDS.toHours(diff) < 1L

private fun isLessThanDay(diff: Long): Boolean = TimeUnit.MILLISECONDS.toDays(diff) < 1L

private fun isYesterday(diff: Long): Boolean = TimeUnit.MILLISECONDS.toDays(diff) == 1L

private fun isSameYear(ms: Long): Boolean {
    val now = Calendar.getInstance()
    val arg = Calendar.getInstance().apply { timeInMillis = ms }
    return now.get(Calendar.YEAR) == arg.get(Calendar.YEAR)
}