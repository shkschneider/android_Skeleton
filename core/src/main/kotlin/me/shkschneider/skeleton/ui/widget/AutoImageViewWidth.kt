package me.shkschneider.skeleton.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

// <http://stackoverflow.com/a/12283909>
class AutoImageViewWidth : AppCompatImageView {

    var ratio = 1.1.toFloat()

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val drawable = drawable ?: run {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = Math.ceil((width.toFloat() * drawable.intrinsicHeight.toFloat() / drawable.intrinsicWidth.toFloat()).toDouble()).toInt()
        setMeasuredDimension((ratio * width).toInt(), (ratio * height).toInt())
    }

}
