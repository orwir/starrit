package orwir.starrit.content.internal.adapter

import com.squareup.moshi.FromJson
import orwir.starrit.content.model.Vote

class VoteAdapter {
    @FromJson
    fun from(raw: Boolean?): Vote = when (raw) {
        true -> Vote.Up
        false -> Vote.Down
        else -> Vote.None
    }
}