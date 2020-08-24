package me.shkschneider.skeleton.ui.widget

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner

// <http://stackoverflow.com/a/11227847>
class MySpinner : AppCompatSpinner {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, mode: Int ) : super(context, attrs, defStyleAttr, mode)

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int, mode: Int = 0, popupTheme: Resources.Theme? = null) : super(context, attrs, defStyleAttr, mode, popupTheme)

    override fun setSelection(position: Int) {
        super.setSelection(position)
        onItemSelectedListener?.onItemSelected(null, null, position, 0)
    }

}
