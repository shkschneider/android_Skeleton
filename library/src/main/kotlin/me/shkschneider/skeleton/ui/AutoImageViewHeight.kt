package me.shkschneider.skeleton.ui

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import me.shkschneider.skeleton.extensions.isNull

// <http://stackoverflow.com/a/12283909>
class AutoImageViewHeight : AppCompatImageView {

    var ratio = 1.1.toFloat()

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (drawable.isNull()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        val width = Math.ceil((height.toFloat() * drawable.intrinsicHeight.toFloat() / drawable.intrinsicWidth.toFloat()).toDouble()).toInt()
        setMeasuredDimension((ratio * width).toInt(), (ratio * height).toInt())
    }

}
