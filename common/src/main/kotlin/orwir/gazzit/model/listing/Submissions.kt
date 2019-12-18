package orwir.gazzit.model.listing

data class Submissions(
    val before: String?,
    val after: String?,
    val modhash: String?,
    val children: List<Response<Submission>>
)