package orwir.starrit.authorization

import android.net.Uri
import androidx.annotation.MainThread
import kotlinx.coroutines.flow.Flow
import orwir.starrit.authorization.model.Step

@MainThread
interface AuthorizationRepository {

    fun flow(): Flow<Step>
    fun startFlow()
    fun completeFlow(response: Uri)
    fun resetFlow()
    fun anonymousAccess()

}