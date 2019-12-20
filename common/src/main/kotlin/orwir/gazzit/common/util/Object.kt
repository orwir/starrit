package orwir.gazzit.common.util

fun createHashCode(vararg fields: Any?): Int =
    fields
        .map { it?.hashCode()?.times(31) ?: 0 }
        .reduce { hash, field -> hash + field }