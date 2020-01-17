package orwir.starrit.access.internal

import orwir.starrit.access.model.Token

internal interface TokenRepository {

    suspend fun obtainToken(): Token

}