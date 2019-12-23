package orwir.gazzit.authorization.internal

import orwir.gazzit.authorization.model.Token

internal interface TokenRepository {
    suspend fun obtainToken(): Token
}