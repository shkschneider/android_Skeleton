package me.shkschneider.skeleton.ui.widget

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.shkschneider.skeleton.uix.Inflater
import java.util.*

// <https://gist.github.com/unosk/af99b1a97b1.toFloat()48521cee>
abstract class MyRecyclerViewAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH> {

    private val objects: ArrayList<T>
    private val lock = Any()

    constructor(objects: ArrayList<T>) : super() {
        this.objects = objects
    }

    fun items(): List<T> {
        return objects
    }

    fun isEmpty(): Boolean {
        return objects.isEmpty()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return objects.size
    }

    fun getItem(position: Int): T {
        return objects[position]
    }

    fun add(obj: T, notify: Boolean) {
        synchronized(lock) {
            objects.add(obj)
        }
        if (notify) notifyDataSetChanged()
    }

    fun insert(obj: T, index: Int, notify: Boolean) {
        synchronized(lock) {
            objects.add(index, obj)
        }
        if (notify) notifyDataSetChanged()
    }

    fun remove(obj: T, notify: Boolean) {
        synchronized(lock) {
            objects.remove(obj)
        }
        if (notify) notifyDataSetChanged()
    }

    fun addAll(objects: List<T>, notify: Boolean) {
        synchronized(lock) {
            this.objects.addAll(objects)
        }
        if (notify) notifyDataSetChanged()
    }

    fun clear(notify: Boolean) {
        synchronized(lock) {
            objects.clear()
        }
        if (notify) notifyDataSetChanged()
    }

    fun sort(comparator: Comparator<in T>, notify: Boolean) {
        synchronized(lock) {
            objects.sortWith(comparator)
        }
        if (notify) notifyDataSetChanged()
    }

    private class MyViewHolder<T>(private val objects: List<T>) : RecyclerView.Adapter<MyViewHolder<T>.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // Inflated items should have: android:foreground="?android:attr/selectableItemBackground"
            // <http://stackoverflow.com/q/26961147>
            return ViewHolder(Inflater.inflate(parent, android.R.layout.simple_list_item_1))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val obj = objects[position]
            holder.text1.text = obj.toString()
            holder.itemView.setOnClickListener(null)
            holder.itemView.setOnLongClickListener(null)
        }

        override fun getItemCount(): Int {
            return objects.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var text1: TextView = itemView.findViewById(android.R.id.text1)

        }

    }

}
