package orwir.starrit.access

import java.io.IOException

class TokenException(
    message: String? = null,
    cause: Throwable? = null,
    val code: ErrorCode = ErrorCode.UNSPECIFIED
) : IOException(message, cause) {

    enum class ErrorCode {
        ACCESS_DENIED,
        UNSUPPORTED_RESPONSE_TYPE,
        INVALID_SCOPE,
        INVALID_REQUEST,
        UNSPECIFIED
    }

}

fun Exception.isAccessDenied() =
    this is TokenException && code == TokenException.ErrorCode.ACCESS_DENIED