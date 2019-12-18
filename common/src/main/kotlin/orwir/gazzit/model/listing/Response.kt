package orwir.gazzit.model.listing

data class Response<T>(
    val kind: Kind,
    val data: T
)