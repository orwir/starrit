package orwir.gazzit

sealed class UiResponse<T> {
    data class Success<T>(val result: T) : UiResponse<T>()
    data class Failure<T>(val exception: Exception) : UiResponse<T>()
}