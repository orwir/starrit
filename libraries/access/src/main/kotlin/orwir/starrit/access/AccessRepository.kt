package orwir.starrit.access

import orwir.starrit.access.model.AccessType

interface AccessRepository {

    suspend fun type(): AccessType

}