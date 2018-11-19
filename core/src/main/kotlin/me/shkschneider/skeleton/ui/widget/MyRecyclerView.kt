package me.shkschneider.skeleton.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// <https://guides.codepath.com/android/using-the-recyclerview>
class MyRecyclerView : RecyclerView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle)

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setHasFixedSize(true)
        addItemDecoration(MyRecyclerViewSeparator(0, 1)) // dummy
    }

}
