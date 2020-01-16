package orwir.starrit.view.extension

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData

fun <T> AutoCompleteTextView.makeExposedDropdown(@LayoutRes itemLayout: Int, values: Array<T>, default: T) {
    setAdapter(ArrayAdapter<T>(context, itemLayout, values))
    setText(default.toString(), false)
}

@Suppress("UNCHECKED_CAST")
fun <T> AutoCompleteTextView.selection(distinct: Boolean = true): LiveData<T> = liveData {
    // find initial value
    (0 until adapter.count)
        .map { adapter.getItem(it) }
        .find { it.toString() == text.toString() }
        ?.let {
            emit(it as T)
        }

    val clickSource = MutableLiveData<T>()
    emitSource(clickSource)

    onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
        val selected = parent.getItemAtPosition(position) as T
        if (!distinct || selected != latestValue) {
            clickSource.value = selected
        }
    }
}

fun View.onAttachStateChanged(block: (Boolean) -> Unit) {
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {
            block(false)
        }

        override fun onViewAttachedToWindow(v: View?) {
            block(true)
        }
    })
}

fun View.onAttached(block: () -> Unit) {
    onAttachStateChanged { if (it) block() }
}

fun View.onDetached(block: () -> Unit) {
    onAttachStateChanged { if (!it) block() }
}