package orwir.starrit.core.model

sealed class NetworkState {
    object Loading : NetworkState()
    object Success : NetworkState()
    data class Failure(val error: Exception) : NetworkState()
}