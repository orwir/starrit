package orwir.starrit.feature.feed.internal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import orwir.starrit.feature.feed.R
import orwir.starrit.listing.ListingRepository

class SubredditSuggestAdapter(
    private val context: Context,
    private val repository: ListingRepository
) : BaseAdapter(), Filterable, CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val suggested = mutableListOf<String>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.view_dropdown_item, parent, false)
        (view as TextView).text = getItem(position)
        return view
    }

    override fun getItem(position: Int): String = suggested[position]

    override fun getItemId(position: Int): Long = suggested[position].hashCode().toLong()

    override fun getCount(): Int = suggested.size

    override fun getFilter(): Filter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val result = FilterResults()
            if (constraint != null) {
                //repository.suggest(constraint.toString())
            }
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.count ?: 0 > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

    }
}