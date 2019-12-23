package orwir.gazzit.authorization

class TokenException(
    message: String? = null,
    cause: Throwable? = null
) : IllegalStateException(message, cause)