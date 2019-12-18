package orwir.gazzit.model.authorization

import java.util.*

enum class Duration {
    Temporary,
    Permanent;

    fun asParameter() = name.toLowerCase(Locale.ENGLISH)
}