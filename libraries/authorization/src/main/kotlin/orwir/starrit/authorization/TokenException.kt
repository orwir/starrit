package orwir.starrit.authorization

class TokenException(
    message: String? = null,
    cause: Throwable? = null
) : IllegalStateException(message, cause)