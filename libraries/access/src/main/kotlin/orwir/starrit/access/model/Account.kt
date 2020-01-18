package orwir.starrit.access.model

sealed class Account {
    object Anonymous : Account()
    class Authorized() : Account()
}