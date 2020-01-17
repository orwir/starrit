package orwir.starrit.access.model

import java.util.*

internal enum class Duration {
    Temporary,
    Permanent;

    fun asParameter() = name.toLowerCase(Locale.ENGLISH)
}