package orwir.starrit.access

import orwir.starrit.access.model.AccessState
import orwir.starrit.access.model.Account

interface AccessRepository {

    suspend fun state(): AccessState

    suspend fun account(): Account

}