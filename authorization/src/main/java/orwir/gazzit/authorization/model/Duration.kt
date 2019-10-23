package orwir.gazzit.authorization.model

import java.util.*

enum class Duration {
    Temporary,
    Permanent;

    fun asParameter() = name.toLowerCase(Locale.ENGLISH)
}