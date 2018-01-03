package me.shkschneider.skeleton.ui

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.helper.ScreenHelper
import me.shkschneider.skeleton.java.StringHelper

// <https://github.com/IvBaranov/MaterialLetterIcon>
class LetterIcon : View {

    private var shapePaint: Paint = Paint()
    private var letterPaint: Paint = Paint()
    private var shapeColor = Color.BLACK
    private var letter: String? = null
    private var letterColor = Color.WHITE
    private var letterSize = 26 // R.dimen.textSizeBig

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    @TargetApi(AndroidHelper.API_21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        shapePaint = Paint()
        shapePaint.style = Paint.Style.FILL
        shapePaint.isAntiAlias = true
        letterPaint = Paint()
        letterPaint.isAntiAlias = true
        setShapeColor(ThemeHelper.accentColor())
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val layoutParams = layoutParams
        if (layoutParams == null) {
            LogHelper.warning("ViewGroup.LayoutParams was NULL")
            return
        }
        if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            val dp = ScreenHelper.pixelsFromDp(DP.toFloat())
            layoutParams.width = dp
            layoutParams.height = dp
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        val w = measuredWidth / 2
        val h = measuredHeight / 2
        val radius = if (w > h) h else w
        drawCircle(canvas, radius, w, h)
        if (letter != null) {
            drawLetter(canvas, w.toFloat(), h.toFloat())
        }
    }

    private fun drawCircle(canvas: Canvas, radius: Int, width: Int, height: Int) {
        shapePaint.color = shapeColor
        canvas.drawCircle(width.toFloat(), height.toFloat(), radius.toFloat(), shapePaint)
    }

    private fun drawLetter(canvas: Canvas, cx: Float, cy: Float) {
        letterPaint.color = letterColor
        letterPaint.textSize = ScreenHelper.pixelsFromSp(letterSize.toFloat()).toFloat()
        letterPaint.getTextBounds(letter, 0, letter!!.length, RECT)
        canvas.drawText(letter!!, cx - RECT.exactCenterX(), cy - RECT.exactCenterY(), letterPaint)
    }

    fun setShapeColor(@ColorInt color: Int) {
        shapeColor = color
        invalidate()
    }

    fun setLetter(string: String) {
        letter = StringHelper.upper(string.replace("\\s+".toRegex(), ""))
        if (letter!!.isNotEmpty()) {
            letter = letter!!.substring(0, 1)
        }
        invalidate()
    }

    fun setLetterColor(@ColorInt color: Int) {
        letterColor = color
        invalidate()
    }

    fun setLetterSize(@IntRange(from = 0) size: Int) {
        letterSize = size
        invalidate()
    }

    fun setLetterTypeface(typeface: Typeface) {
        letterPaint.typeface = typeface
        invalidate()
    }

    companion object {

        private val DP = 40
        private val RECT = Rect()

    }

}
