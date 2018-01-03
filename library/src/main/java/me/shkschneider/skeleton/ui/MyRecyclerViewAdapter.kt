package me.shkschneider.skeleton.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList
import java.util.Collections
import java.util.Comparator

// <https://gist.github.com/unosk/af99b1a97b2f48521cee>
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
            Collections.sort(objects, comparator)
        }
        if (notify) notifyDataSetChanged()
    }

    private class MyViewHolder<T>(private val objects: List<T>) : RecyclerView.Adapter<MyViewHolder<T>.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
            // Inflated items should have: android:foreground="?android:attr/selectableItemBackground"
            // <http://stackoverflow.com/q/26961147>
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder<T>.ViewHolder, position: Int) {
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
