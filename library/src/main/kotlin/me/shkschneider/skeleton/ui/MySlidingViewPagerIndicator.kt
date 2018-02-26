package me.shkschneider.skeleton.ui

import android.content.Context
import android.content.res.Resources
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

// <https://github.com/google/iosched>
class MySlidingViewPagerIndicator : HorizontalScrollView {

    private val _slidingTabStrib: SlidingTabStrip
    private val _titleOffset: Int
    private var _colorizer: Colorizer? = null
    private var _distributeEvenly: Boolean = false
    private var _viewPager: ViewPager? = null

    init {
        isHorizontalScrollBarEnabled = false
        isFillViewport = true
        _titleOffset = (TITLE_OFFSET_DIPS * Resources.getSystem().displayMetrics.density).toInt()
        _slidingTabStrib = SlidingTabStrip(context)
        addView(_slidingTabStrib, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle)

    fun setColorizer(colorizer: Colorizer) {
        _colorizer = colorizer
        _slidingTabStrib.setColorizer(_colorizer)
    }

    fun setDistributeEvenly(distributeEvenly: Boolean) {
        _distributeEvenly = distributeEvenly
    }

    fun setViewPager(viewPager: ViewPager) {
        _slidingTabStrib.removeAllViews()
        _viewPager = viewPager
        if (_viewPager != null) {
            _viewPager!!.addOnPageChangeListener(InternalViewPagerListener())
            val pagerAdapter = _viewPager!!.adapter
            val onClickListener = TabClickListener()
            for (i in 0 until pagerAdapter.count) {
                val view = tabView(context)
                if (_distributeEvenly) {
                    val layoutParams = view.layoutParams as LinearLayout.LayoutParams
                    layoutParams.width = 0
                    layoutParams.weight = 1f
                }
                if (TextView::class.java.isInstance(view)) {
                    view.setTextColor(if (_colorizer == null) DEFAULT_TEXTCOLOR else _colorizer!!.getTextColor(i))
                    view.text = pagerAdapter.getPageTitle(i)
                }
                view.setOnClickListener(onClickListener)
                _slidingTabStrib.addView(view)
                view.isSelected = i == _viewPager!!.currentItem
            }
        }
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
        val padding = (TAB_VIEW_PADDING_DIPS * Resources.getSystem().displayMetrics.density).toInt()
        textView.setPadding(padding, padding, padding, padding)
        return textView
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (_viewPager != null) {
            scroll(_viewPager!!.currentItem, 0)
        }
    }

    private fun scroll(tab: Int, offset: Int) {
        val childCount = _slidingTabStrib.childCount
        if (childCount == 0 || tab < 0 || tab >= childCount) {
            return
        }
        val child = _slidingTabStrib.getChildAt(tab)
        if (child != null) {
            var scroll = child.left + offset
            if (tab > 0 || offset > 0) {
                scroll -= _titleOffset
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
            for (i in 0 until _slidingTabStrib.childCount) {
                if (view === _slidingTabStrib.getChildAt(i)) {
                    _viewPager!!.currentItem = i
                    return
                }
            }
        }

    }

    private inner class InternalViewPagerListener : ViewPager.OnPageChangeListener {

        private var _scrollState: Int = 0

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            val childCount = _slidingTabStrib.childCount
            if (childCount == 0 || position < 0 || position >= childCount) {
                return
            }
            _slidingTabStrib.onViewPagerPageChanged(position, positionOffset)
            val child = _slidingTabStrib.getChildAt(position)
            val offset = if (child != null) (positionOffset * child.width).toInt() else 0
            scroll(position, offset)
        }

        override fun onPageScrollStateChanged(state: Int) {
            _scrollState = state
        }

        override fun onPageSelected(position: Int) {
            if (_scrollState == ViewPager.SCROLL_STATE_IDLE) {
                _slidingTabStrib.onViewPagerPageChanged(position, 0f)
                scroll(position, 0)
            }
            for (i in 0 until _slidingTabStrib.childCount) {
                _slidingTabStrib.getChildAt(i).isSelected = position == i
            }
        }

    }

    private class SlidingTabStrip constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

        private val _bottomBorderThickness: Int
        private val _bottomBorderPaint: Paint
        private val _selectedIndicatorThickness: Int
        private val _selectedIndicatorPaint: Paint
        private val _dividerHeight: Float
        private val _dividerPaint: Paint

        private var _selectedPosition: Int = 0
        private var _selectionOffset: Float = 0.toFloat()
        private var _colorizer: Colorizer? = null

        init {
            setWillNotDraw(false)
            val density = Resources.getSystem().displayMetrics.density
            val typedValue = TypedValue()
            context.theme.resolveAttribute(android.R.attr.colorForeground, typedValue, true)
            _bottomBorderThickness = (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density).toInt()
            _bottomBorderPaint = Paint()
            _bottomBorderPaint.color = SlidingTabStrip.setColorAlpha(typedValue.data, DEFAULT_BOTTOM_BORDER_COLOR_ALPHA)
            _selectedIndicatorThickness = (SELECTED_INDICATOR_THICKNESS_DIPS * density).toInt()
            _selectedIndicatorPaint = Paint()
            _dividerHeight = DEFAULT_DIVIDER_HEIGHT
            _dividerPaint = Paint()
            _dividerPaint.strokeWidth = (DEFAULT_DIVIDER_THICKNESS_DIPS * density).toInt().toFloat()
        }

        internal fun setColorizer(colorizer: Colorizer?) {
            _colorizer = colorizer
            invalidate()
        }

        internal fun onViewPagerPageChanged(position: Int, positionOffset: Float) {
            _selectedPosition = position
            _selectionOffset = positionOffset
            invalidate()
        }

        override fun onDraw(canvas: Canvas) {
            val height = height
            val childCount = childCount
            val dividerHeight = (Math.min(Math.max(0f, _dividerHeight), 1f) * height).toInt()
            if (childCount > 0) {
                val selectedTitle = getChildAt(_selectedPosition)
                var left = selectedTitle.left
                var right = selectedTitle.right
                var color = _colorizer!!.getIndicatorColor(_selectedPosition)
                if (_selectionOffset > 0f && _selectedPosition < getChildCount() - 1) {
                    val nextColor = _colorizer!!.getIndicatorColor(_selectedPosition + 1)
                    if (color != nextColor) {
                        color = blendColors(nextColor, color, _selectionOffset)
                    }
                    val view = getChildAt(_selectedPosition + 1)
                    left = (_selectionOffset * view.left + (1.0f - _selectionOffset) * left).toInt()
                    right = (_selectionOffset * view.right + (1.0f - _selectionOffset) * right).toInt()
                }
                _selectedIndicatorPaint.color = color
                canvas.drawRect(left.toFloat(), (height - _selectedIndicatorThickness).toFloat(), right.toFloat(), height.toFloat(), _selectedIndicatorPaint)
            }
            canvas.drawRect(0f, (height - _bottomBorderThickness).toFloat(), width.toFloat(), height.toFloat(), _bottomBorderPaint)
            val divider = (height - dividerHeight) / 2
            for (i in 0 until childCount - 1) {
                val child = getChildAt(i)
                _dividerPaint.color = _colorizer!!.getDividerColor(i)
                canvas.drawLine(child.right.toFloat(), divider.toFloat(), child.right.toFloat(), (divider + dividerHeight).toFloat(), _dividerPaint)
            }
        }

        companion object {

            private val DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0
            private val DEFAULT_BOTTOM_BORDER_COLOR_ALPHA: Byte = 0x26
            private val SELECTED_INDICATOR_THICKNESS_DIPS = 4
            private val DEFAULT_DIVIDER_THICKNESS_DIPS = 1
            private val DEFAULT_DIVIDER_HEIGHT = 0.5f

            private fun setColorAlpha(color: Int, alpha: Byte): Int {
                return Color.argb(alpha.toInt(), Color.red(color), Color.green(color), Color.blue(color))
            }

            private fun blendColors(color1: Int, color2: Int, ratio: Float): Int {
                val inverseRation = 1f - ratio
                val r = Color.red(color1) * ratio + Color.red(color2) * inverseRation
                val g = Color.green(color1) * ratio + Color.green(color2) * inverseRation
                val b = Color.blue(color1) * ratio + Color.blue(color2) * inverseRation
                return Color.rgb(r.toInt(), g.toInt(), b.toInt())
            }
        }

    }

    companion object {

        private val DEFAULT_TEXTCOLOR = Color.BLACK
        private val TITLE_OFFSET_DIPS = 24
        private val TAB_VIEW_PADDING_DIPS = 16
        private val TAB_VIEW_TEXT_SIZE_SP = 12

    }

}
