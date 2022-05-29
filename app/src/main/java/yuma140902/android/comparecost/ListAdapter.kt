package yuma140902.android.comparecost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

data class RowItem(val model: RowModel, var viewLogic: RowViewLogic? = null)

class ListAdapter(
    private val context_: Context,
    private val onItemUpdated: (RowViewLogic) -> Unit
) : ArrayAdapter<RowItem>(context_, 0) {
    private val inflater: LayoutInflater = LayoutInflater.from(context_)

    // see: https://developer.android.com/reference/android/widget/Adapter#getView(int,%20android.view.View,%20android.view.ViewGroup)
    // > View: The old view to reuse, if possible. Note: You should check that this view is non-null
    // > and of an appropriate type before using. If it is not possible to convert this view to
    // > display the correct data, this method can create a new view. Heterogeneous lists can
    // > specify their number of view types, so that this View is always of the right type (see
    // > getViewTypeCount() and getItemViewType(int)).
    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.list_row, parent, false)

        val item = getItem(position) ?: RowItem(RowModel())
        item.viewLogic = item.viewLogic ?: RowViewLogic(
            view.findViewById(R.id.yenEditNumber),
            view.findViewById(R.id.amountEditNumber),
            view.findViewById(R.id.yenTextView),
            item.model,
            onItemUpdated
        )
        item.viewLogic?.updateView()

        return view
    }
}