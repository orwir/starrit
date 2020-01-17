package orwir.starrit.access

import android.net.Uri
import androidx.annotation.MainThread
import kotlinx.coroutines.flow.Flow
import orwir.starrit.access.model.Step

@MainThread
interface AuthorizationRepository {

    fun flow(): Flow<Step>
    fun startFlow()
    fun completeFlow(response: Uri)
    fun resetFlow()
    fun anonymousAccess()

}