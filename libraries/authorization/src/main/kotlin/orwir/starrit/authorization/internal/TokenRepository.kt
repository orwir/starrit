package orwir.starrit.authorization.internal

import orwir.starrit.authorization.model.Token

internal interface TokenRepository {

    suspend fun obtainToken(): Token

}