package orwir.gazzit.listing.adaper

import com.squareup.moshi.FromJson
import orwir.gazzit.listing.model.Vote

class VoteAdapter {
    @FromJson
    fun from(raw: Boolean?): Vote = when (raw) {
        true -> Vote.Up
        false -> Vote.Down
        else -> Vote.None
    }
}