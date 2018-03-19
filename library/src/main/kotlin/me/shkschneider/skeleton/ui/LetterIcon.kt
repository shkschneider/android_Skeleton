package me.shkschneider.skeleton.ui

import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import me.shkschneider.skeleton.helper.Logger
import me.shkschneider.skeleton.helper.Metrics

// <https://github.com/IvBaranov/MaterialLetterIcon>
class LetterIcon : View {

    private val DEFAULT = "#"
    private val DP = 40
    private val RECT = Rect()

    private var shapePaint = Paint()
    private var letterPaint = Paint()
    private var shapeColor = Color.BLACK
    private var letter = DEFAULT
    private var letterColor = Color.WHITE
    private var letterSize = 26 // R.dimen.textSizeBig

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

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
            Logger.warning("ViewGroup.LayoutParams was NULL")
            return
        }
        if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            val dp = Metrics.pixelsFromDp(DP.toFloat())
            layoutParams.width = dp
            layoutParams.height = dp
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        val width = measuredWidth / 2
        val height = measuredHeight / 2
        val radius = if (width > height) height else width
        with (shapePaint) {
            color = shapeColor
            canvas.drawCircle(width.toFloat(), height.toFloat(), radius.toFloat(), this)
        }
        with (letterPaint) {
            color = letterColor
            textSize = Metrics.pixelsFromSp(letterSize.toFloat()).toFloat()
            getTextBounds(letter, 0, letter.length, RECT)
            canvas.drawText(letter, width - RECT.exactCenterX(), height - RECT.exactCenterY(), this)
        }
    }

    fun setShapeColor(@ColorInt color: Int) {
        shapeColor = color
        invalidate()
    }

    fun setLetter(string: String) {
        letter = string.replace("\\s+".toRegex(), "").toUpperCase()
        letter = if (letter.isNotEmpty()) letter.first().toString() else DEFAULT
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

}
