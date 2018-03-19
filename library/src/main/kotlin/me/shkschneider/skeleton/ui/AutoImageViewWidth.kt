package me.shkschneider.skeleton.ui

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View

// <http://stackoverflow.com/a/12283909>
class AutoImageViewWidth : AppCompatImageView {

    var ratio = 1.1.toFloat()

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val drawable = drawable
        if (drawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = Math.ceil((width.toFloat() * drawable.intrinsicHeight.toFloat() / drawable.intrinsicWidth.toFloat()).toDouble()).toInt()
        setMeasuredDimension((ratio * width).toInt(), (ratio * height).toInt())
    }

}
