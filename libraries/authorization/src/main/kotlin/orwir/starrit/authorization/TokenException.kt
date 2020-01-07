package orwir.starrit.authorization

class TokenException(
    message: String? = null,
    cause: Throwable? = null,
    val code: ErrorCode = ErrorCode.UNKNOWN
) : IllegalStateException(message, cause) {

    enum class ErrorCode {
        ACCESS_DENIED,
        UNSUPPORTED_RESPONSE_TYPE,
        INVALID_SCOPE,
        INVALID_REQUEST,
        UNKNOWN
    }

}