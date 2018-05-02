package me.shkschneider.skeleton.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import me.shkschneider.skeleton.extensions.then

abstract class SquaredView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    // <https://stackoverflow.com/a/8981478>
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val size = (width > height) then height ?: width
        setMeasuredDimension(size, size)
    }

}