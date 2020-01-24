package orwir.starrit.content.internal.adapter

import com.squareup.moshi.FromJson
import orwir.starrit.content.model.Kind

class KindAdapter {
    @FromJson
    fun from(raw: String): Kind = Kind
        .values()
        .find { it.raw == raw }
        ?: throw IllegalArgumentException("Kind [$raw] not registered!")
}