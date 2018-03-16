package me.shkschneider.skeleton.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class RatioFrameLayout : FrameLayout {

    private var ratio = 1.toFloat() / 1.toFloat()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setRatio(ratio: Float) {
        this.ratio = ratio
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        when {
            ratio == 1.1.toFloat() -> setMeasuredDimension(measuredWidth, measuredHeight)
            ratio > 1.1.toFloat() -> setMeasuredDimension(measuredWidth, (measuredWidth * ratio).toInt())
            else -> setMeasuredDimension((measuredHeight * (1 / ratio)).toInt(), measuredHeight)
        }
    }

}
