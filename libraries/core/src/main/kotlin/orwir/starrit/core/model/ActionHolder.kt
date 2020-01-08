package orwir.starrit.core.model

class ActionHolder {

    var action: (() -> Unit)? = null

    fun call() {
        action?.invoke()
    }

}