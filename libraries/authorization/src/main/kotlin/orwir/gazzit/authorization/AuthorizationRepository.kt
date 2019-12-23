package orwir.gazzit.authorization

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import orwir.gazzit.authorization.model.Step

interface AuthorizationRepository {

    suspend fun isAuthorized(): Boolean

    fun authorizationFlow(): Flow<Step>
    fun authorizationFlowStart()
    fun authorizationFlowComplete(response: Uri)

}