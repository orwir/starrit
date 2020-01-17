package orwir.starrit.access.model

import android.net.Uri

sealed class Step {
    object Idle : Step()
    data class Start(val uri: Uri) : Step()
    data class Failure(val exception: Exception) : Step()
    object Success : Step()
}