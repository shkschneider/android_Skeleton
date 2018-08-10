package me.shkschneider.skeleton.ui

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

/**
    <AutoCompleteTextView
        android:id="@+id/autoCompleteTextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="text"
        android:singleLine="true"
        android:hint="@android:string/search_go" />
*/

open class AutoCompleteSearchManager<T:Any>(
    context: Context,
    private val filter: Filter,
    @LayoutRes private val layoutId: Int = android.R.layout.simple_dropdown_item_1line,
    private val viewHolderMapper: IViewHolderMapper<T>
) : ArrayAdapter<T>(context, layoutId) {

    private var items: List<T>? = null // FIXME set

    override fun getCount(): Int {
        return if (items == null) 0 else items!!.size
    }

    override fun getItem(position: Int): T? {
        return items!![position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        }
        with(view!!) {
            getItem(position)?.let {
                viewHolderMapper.getViewHolder(position, view, it)
            }
        }
        return view
    }

    override fun getFilter(): Filter {
        return filter
    }

    interface IViewHolderMapper<in T:Any> {

        fun getViewHolder(position: Int, view: View, item: T): RecyclerView.ViewHolder?

    }

}
