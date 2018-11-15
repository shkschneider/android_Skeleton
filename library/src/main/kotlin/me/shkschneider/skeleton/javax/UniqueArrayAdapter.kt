package me.shkschneider.skeleton.javax

import android.content.Context
import android.widget.ArrayAdapter

// TODO improve and test
open class UniqueArrayAdapter<T>(
        context: Context,
        layout: Int,
        items: List<T>
) : ArrayAdapter<T>(context, layout, items.distinct()) {

    override fun add(item: T?) {
        item ?: return
        val items = arrayListOf<T>()
        for (position in 0 until count) {
            getItem(position)?.let {
                items.add(it)
            }
        }
        items.add(item)
        clear()
        super.addAll(items.distinct())
    }

    override fun addAll(collection: MutableCollection<out T>) {
        collection.forEach { item ->
            add(item)
        }
    }

    override fun addAll(vararg items: T) {
        items.forEach { item ->
            add(item)
        }
    }

}
