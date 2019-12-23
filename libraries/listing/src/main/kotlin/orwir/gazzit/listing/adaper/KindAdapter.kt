package orwir.gazzit.listing.adaper

import com.squareup.moshi.FromJson
import orwir.gazzit.listing.model.Kind

class KindAdapter {
    @FromJson
    fun from(raw: String): Kind = Kind
        .values()
        .find { it.raw == raw }
        ?: throw IllegalArgumentException("Kind [$raw] not registered!")
}