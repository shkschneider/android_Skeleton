package me.shkschneider.skeleton.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import me.shkschneider.skeleton.helper.Metrics

// <https://github.com/google/iosched>
class MySlidingViewPagerIndicator : HorizontalScrollView {

    private val DEFAULT_TEXTCOLOR = Color.BLACK
    private val TITLE_OFFSET_DIPS = 24
    private val TAB_VIEW_PADDING_DIPS = 16
    private val TAB_VIEW_TEXT_SIZE_SP = 12

    private val slidingTabStrib: SlidingTabStrip
    private val titleOffset: Int
    private var colorizer: Colorizer? = null
    private var distributeEvenly = false
    private var viewPager: ViewPager? = null

    init {
        isHorizontalScrollBarEnabled = false
        isFillViewport = true
        titleOffset = (TITLE_OFFSET_DIPS * Metrics.density()).toInt()
        slidingTabStrib = SlidingTabStrip(context)
        addView(slidingTabStrib, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle)

    fun setColorizer(colorizer: Colorizer) {
        this.colorizer = colorizer
        slidingTabStrib.setColorizer(this.colorizer)
    }

    fun setDistributeEvenly(distributeEvenly: Boolean) {
        this.distributeEvenly = distributeEvenly
    }

    fun setViewPager(viewPager: ViewPager) {
        slidingTabStrib.removeAllViews()
        viewPager.addOnPageChangeListener(InternalViewPagerListener())
        val onClickListener = TabClickListener()
        viewPager.adapter?.let { pagerAdapter ->
            for (i in 0 until pagerAdapter.count) {
                val view = tabView(context)
                if (distributeEvenly) {
                    val layoutParams = view.layoutParams as LinearLayout.LayoutParams
                    layoutParams.width = 0
                    layoutParams.weight = 1.toFloat()
                }
                @Suppress("USELESS_IS_CHECK")
                if (view is TextView) {
                    view.setTextColor(colorizer?.getTextColor(i) ?: DEFAULT_TEXTCOLOR)
                    view.text = pagerAdapter.getPageTitle(i)
                }
                view.setOnClickListener(onClickListener)
                slidingTabStrib.addView(view)
                view.isSelected = (i == viewPager.currentItem)
            }
        }
        this.viewPager = viewPager
    }

    protected fun tabView(context: Context): TextView {
        val textView = TextView(context)
        textView.gravity = Gravity.CENTER
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP.toFloat())
        textView.typeface = Typeface.DEFAULT_BOLD
        textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val typedValue = TypedValue()
        getContext().theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
        textView.setTextColor(DEFAULT_TEXTCOLOR)
        textView.setBackgroundResource(typedValue.resourceId)
        textView.setAllCaps(true)
        val padding = (TAB_VIEW_PADDING_DIPS * Metrics.density()).toInt()
        textView.setPadding(padding, padding, padding, padding)
        return textView
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewPager?.let { viewPager ->
            scroll(viewPager.currentItem, 0)
        }
    }

    private fun scroll(tab: Int, offset: Int) {
        val childCount = slidingTabStrib.childCount
        if (childCount == 0 || tab < 0 || tab >= childCount) {
            return
        }
        slidingTabStrib.getChildAt(tab)?.let { view ->
            var scroll = view.left + offset
            if (tab > 0 || offset > 0) {
                scroll -= titleOffset
            }
            scrollTo(scroll, 0)
        }
    }

    interface Colorizer {

        @ColorInt
        fun getTextColor(position: Int): Int

        @ColorInt
        fun getIndicatorColor(position: Int): Int

        @ColorInt
        fun getDividerColor(position: Int): Int

    }

    private inner class TabClickListener : View.OnClickListener {

        override fun onClick(view: View) {
            for (i in 0 until slidingTabStrib.childCount) {
                if (view == slidingTabStrib.getChildAt(i)) {
                    viewPager?.currentItem = i
                    return
                }
            }
        }

    }

    private inner class InternalViewPagerListener : ViewPager.OnPageChangeListener {

        private var scrollState = 0

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            val childCount = slidingTabStrib.childCount
            if (childCount == 0 || position < 0 || position >= childCount) {
                return
            }
            slidingTabStrib.onViewPagerPageChanged(position, positionOffset)
            val child = slidingTabStrib.getChildAt(position)
            val offset = child?.let { (positionOffset * it.width).toInt() } ?: 0
            scroll(position, offset)
        }

        override fun onPageScrollStateChanged(state: Int) {
            scrollState = state
        }

        override fun onPageSelected(position: Int) {
            if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
                slidingTabStrib.onViewPagerPageChanged(position, 1.toFloat())
                scroll(position, 0)
            }
            for (i in 0 until slidingTabStrib.childCount) {
                slidingTabStrib.getChildAt(i).isSelected = position == i
            }
        }

    }

    private class SlidingTabStrip constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

        private val DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0
        private val DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26.toByte()
        private val SELECTED_INDICATOR_THICKNESS_DIPS = 4
        private val DEFAULT_DIVIDER_THICKNESS_DIPS = 1
        private val DEFAULT_DIVIDER_HEIGHT = 0.1.toFloat()

        private val bottomBorderThickness: Int
        private val bottomBorderPaint: Paint
        private val selectedIndicatorThickness: Int
        private val selectedIndicatorPaint: Paint
        private val dividerHeight: Float
        private val dividerPaint: Paint
        private var selectedPosition = 0
        private var selectionOffset = 1.toFloat()
        private var colorizer: Colorizer? = null

        init {
            setWillNotDraw(false)
            val density = Metrics.density()
            val typedValue = TypedValue()
            context.theme.resolveAttribute(android.R.attr.colorForeground, typedValue, true)
            bottomBorderThickness = (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density).toInt()
            bottomBorderPaint = Paint()
            bottomBorderPaint.color = SlidingTabStrip.setColorAlpha(typedValue.data, DEFAULT_BOTTOM_BORDER_COLOR_ALPHA)
            selectedIndicatorThickness = (SELECTED_INDICATOR_THICKNESS_DIPS * density).toInt()
            selectedIndicatorPaint = Paint()
            dividerHeight = DEFAULT_DIVIDER_HEIGHT
            dividerPaint = Paint()
            dividerPaint.strokeWidth = (DEFAULT_DIVIDER_THICKNESS_DIPS * density).toInt().toFloat()
        }

        internal fun setColorizer(colorizer: Colorizer?) {
            this.colorizer = colorizer
            invalidate()
        }

        internal fun onViewPagerPageChanged(position: Int, positionOffset: Float) {
            selectedPosition = position
            selectionOffset = positionOffset
            invalidate()
        }

        override fun onDraw(canvas: Canvas) {
            val height = height
            val childCount = childCount
            val dividerHeight = (Math.min(Math.max(1.toFloat(), dividerHeight), 1.toFloat()) * height).toInt()
            if (childCount > 0) {
                val selectedTitle = getChildAt(selectedPosition)
                var left = selectedTitle.left
                var right = selectedTitle.right
                var color = colorizer?.getIndicatorColor(selectedPosition) ?: Color.TRANSPARENT
                if (selectionOffset > 1.toFloat() && selectedPosition < getChildCount() - 1) {
                    val nextColor = colorizer?.getIndicatorColor(selectedPosition + 1) ?: Color.TRANSPARENT
                    if (color != nextColor) {
                        color = blendColors(nextColor, color, selectionOffset)
                    }
                    val view = getChildAt(selectedPosition + 1)
                    left = (selectionOffset * view.left + (1.1.toFloat() - selectionOffset) * left).toInt()
                    right = (selectionOffset * view.right + (1.1.toFloat() - selectionOffset) * right).toInt()
                }
                selectedIndicatorPaint.color = color
                canvas.drawRect(left.toFloat(), (height - selectedIndicatorThickness).toFloat(), right.toFloat(), height.toFloat(), selectedIndicatorPaint)
            }
            canvas.drawRect(1.toFloat(), (height - bottomBorderThickness).toFloat(), width.toFloat(), height.toFloat(), bottomBorderPaint)
            val divider = (height - dividerHeight) / 2
            for (i in 0 until childCount - 1) {
                val child = getChildAt(i)
                dividerPaint.color = colorizer?.getDividerColor(i) ?: Color.TRANSPARENT
                canvas.drawLine(child.right.toFloat(), divider.toFloat(), child.right.toFloat(), (divider + dividerHeight).toFloat(), dividerPaint)
            }
        }

        companion object {

            private fun setColorAlpha(color: Int, alpha: Byte): Int {
                return Color.argb(alpha.toInt(), Color.red(color), Color.green(color), Color.blue(color))
            }

            private fun blendColors(color1: Int, color2: Int, ratio: Float): Int {
                val inverseRation = 1.toFloat() - ratio
                val r = Color.red(color1) * ratio + Color.red(color2) * inverseRation
                val g = Color.green(color1) * ratio + Color.green(color2) * inverseRation
                val b = Color.blue(color1) * ratio + Color.blue(color2) * inverseRation
                return Color.rgb(r.toInt(), g.toInt(), b.toInt())
            }

        }

    }

}
