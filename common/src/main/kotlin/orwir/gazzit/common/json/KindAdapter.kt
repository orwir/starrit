package orwir.gazzit.common.json

import com.squareup.moshi.FromJson
import orwir.gazzit.model.listing.Kind

class KindAdapter {
    @FromJson
    fun from(raw: String): Kind = Kind
        .values()
        .find { it.raw == raw }
        ?: throw IllegalArgumentException("Kind [$raw] not registered!")
}