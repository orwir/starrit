package orwir.starrit.authorization

fun Exception.isAccessDenied() = this is TokenException && code == TokenException.ErrorCode.ACCESS_DENIED