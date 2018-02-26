package me.shkschneider.skeleton.ui

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.AppCompatSpinner
import android.util.AttributeSet
import android.widget.AdapterView

// <http://stackoverflow.com/a/11227847>
class MySpinner : AppCompatSpinner {

    private var mOnItemSelectedListener: AdapterView.OnItemSelectedListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, mode: Int) : super(context, mode)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, mode: Int) : super(context, attrs, defStyleAttr, mode)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, mode: Int, popupTheme: Resources.Theme) : super(context, attrs, defStyleAttr, mode, popupTheme)

    override fun setSelection(position: Int) {
        super.setSelection(position)
        if (mOnItemSelectedListener != null) {
            mOnItemSelectedListener!!.onItemSelected(null, null, position, 0)
        }
    }

    override fun setOnItemSelectedListener(listener: AdapterView.OnItemSelectedListener?) {
        mOnItemSelectedListener = listener
    }

}
