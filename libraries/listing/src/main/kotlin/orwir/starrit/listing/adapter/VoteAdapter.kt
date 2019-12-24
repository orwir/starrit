package orwir.starrit.listing.adapter

import com.squareup.moshi.FromJson
import orwir.starrit.listing.model.Vote

class VoteAdapter {
    @FromJson
    fun from(raw: Boolean?): Vote = when (raw) {
        true -> Vote.Up
        false -> Vote.Down
        else -> Vote.None
    }
}