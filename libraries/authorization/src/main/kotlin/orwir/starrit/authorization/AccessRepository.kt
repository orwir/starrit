package orwir.starrit.authorization

import orwir.starrit.authorization.model.AccessType

interface AccessRepository {

    suspend fun accessType(): AccessType

}