package orwir.gazzit.common

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import java.util.*

val commonModule = module {

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

}

private val squeeze_suffixes: NavigableMap<Long, String> = TreeMap(
    mapOf(
        1_000L to "k",
        1_000_000L to "M"
    )
)

fun Long.squeezeFormat(): String {
    if (this == Long.MIN_VALUE) return (Long.MIN_VALUE + 1).squeezeFormat()
    if (this < 0) return "-${(-this).squeezeFormat()}"
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

fun Int.squeezeFormat(): String = this.toLong().squeezeFormat()