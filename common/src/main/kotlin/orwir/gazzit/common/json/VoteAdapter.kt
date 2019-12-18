package orwir.gazzit.common.json

import com.squareup.moshi.FromJson
import orwir.gazzit.model.listing.Vote

class VoteAdapter {
    @FromJson
    fun from(raw: Boolean?): Vote = when (raw) {
        true -> Vote.Up
        false -> Vote.Down
        else -> Vote.None
    }
}