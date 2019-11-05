package orwir.gazzit.common

import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun TextView.asVar() = object : ReadWriteProperty<Any, CharSequence> {
    override fun getValue(thisRef: Any, property: KProperty<*>): CharSequence = this@asVar.text

    override fun setValue(thisRef: Any, property: KProperty<*>, value: CharSequence) {
        this@asVar.text = value
    }
}