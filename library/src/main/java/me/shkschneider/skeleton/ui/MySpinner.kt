package me.shkschneider.skeleton.ui

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner

// <http://stackoverflow.com/a/11227847>
class MySpinner : AppCompatSpinner {

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, mode: Int = 0) : super(context, attrs, defStyleAttr, mode)

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int, mode: Int, popupTheme: Resources.Theme) : super(context, attrs, defStyleAttr, mode, popupTheme)

    override fun setSelection(position: Int) {
        super.setSelection(position)
        onItemSelectedListener?.onItemSelected(null, null, position, 0)
    }

}
