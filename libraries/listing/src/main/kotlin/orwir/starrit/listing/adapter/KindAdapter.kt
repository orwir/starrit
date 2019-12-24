package orwir.starrit.listing.adapter

import com.squareup.moshi.FromJson
import orwir.starrit.listing.model.Kind

class KindAdapter {
    @FromJson
    fun from(raw: String): Kind = Kind
        .values()
        .find { it.raw == raw }
        ?: throw IllegalArgumentException("Kind [$raw] not registered!")
}