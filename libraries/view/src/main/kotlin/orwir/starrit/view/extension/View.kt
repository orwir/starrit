package orwir.starrit.view.extension

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.LayoutRes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

fun <T> AutoCompleteTextView.makeExposedDropdown(@LayoutRes itemLayout: Int, values: Array<T>, default: T) {
    setAdapter(ArrayAdapter<T>(context, itemLayout, values))
    setText(default.toString(), false)
}

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
fun <T> AutoCompleteTextView.selectionFlow() = callbackFlow {
    var latest: T? = null

    // find initial value
    (0 until adapter.count)
        .map { adapter.getItem(it) }
        .find { it.toString() == text.toString() }
        ?.let {
            latest = it as T
            send(it as T)
        }

    onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
        launch {
            val selected = parent.getItemAtPosition(position) as T
            if (selected != latest) {
                latest = selected
                send(selected)
            }
        }
    }

    awaitClose {
        onItemSelectedListener = null
    }

}