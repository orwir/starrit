package orwir.starrit.authorization

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