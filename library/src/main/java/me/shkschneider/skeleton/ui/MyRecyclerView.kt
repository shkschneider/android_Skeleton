package me.shkschneider.skeleton.ui

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

// <https://guides.codepath.com/android/using-the-recyclerview>
class MyRecyclerView : RecyclerView {

    constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle)

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setHasFixedSize(true)
        addItemDecoration(MyRecyclerViewSeparator(context, MyRecyclerViewSeparator.VERTICAL))
    }

}
