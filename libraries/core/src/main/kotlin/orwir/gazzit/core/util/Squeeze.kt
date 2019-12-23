package orwir.gazzit.core.util

import java.util.*

private val squeeze_suffixes: NavigableMap<Long, String> = TreeMap(
    mapOf(
        1_000L to "k",
        1_000_000L to "M"
    )
)

fun Long.squeeze(): String {
    if (this == Long.MIN_VALUE) return (Long.MIN_VALUE + 1).squeeze()
    if (this < 0) return "-${(-this).squeeze()}"
    if (this < 1_000) return this.toString()

    val entry = squeeze_suffixes.floorEntry(this) ?: return this.toString()

    val truncated = this / (entry.key / 10)
    val hasDecimal = truncated < 100 && (truncated / 10F) != (truncated / 10).toFloat()
    return if (hasDecimal) {
        "${truncated / 10F}${entry.value}"
    } else {
        "${truncated / 10L}${entry.value}"
    }
}

fun Int.squeeze(): String = this.toLong().squeeze()