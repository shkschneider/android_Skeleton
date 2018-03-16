package me.shkschneider.skeleton.ui

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.AppCompatSpinner
import android.util.AttributeSet

// <http://stackoverflow.com/a/11227847>
class MySpinner : AppCompatSpinner {

    constructor(context: Context) : super(context)

    constructor(context: Context, mode: Int) : super(context, mode)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, mode: Int) : super(context, attrs, defStyleAttr, mode)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, mode: Int, popupTheme: Resources.Theme) : super(context, attrs, defStyleAttr, mode, popupTheme)

    override fun setSelection(position: Int) {
        super.setSelection(position)
        onItemSelectedListener?.onItemSelected(null, null, position, 0)
    }

}
