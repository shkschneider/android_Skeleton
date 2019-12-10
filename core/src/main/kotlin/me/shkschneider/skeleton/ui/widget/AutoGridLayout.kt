package me.shkschneider.skeleton.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.RecyclerView
import me.shkschneider.skeleton.extensions.android.ViewHelper
import me.shkschneider.skeleton.extensions.has
import me.shkschneider.skeleton.helper.AndroidHelper

private const val HORIZONTAL = LinearLayout.HORIZONTAL
private const val VERTICAL = LinearLayout.VERTICAL

// <https://github.com/AlbertGrobas/AutoLinearLayout>
class AutoGridLayout : FrameLayout {

    private var orientation = 0
        set(orientation) {
            if (orientation != HORIZONTAL && orientation != VERTICAL) {
                return
            }
            if (this.orientation != orientation) {
                field = orientation
                requestLayout()
            }
        }
    private var gravity = Gravity.TOP or Gravity.START
    private val positions = ArrayList<ViewPosition>()

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val orientation = attrs?.getAttributeResourceValue(ViewHelper.ANDROIDXML, "orientation", -1)
                ?: -1
        when (orientation) {
            HORIZONTAL -> setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
            VERTICAL -> setVerticalGravity(Gravity.CENTER_VERTICAL)
            else -> setGravity(Gravity.CENTER)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (orientation == VERTICAL) {
            measureVertical(widthMeasureSpec, heightMeasureSpec)
        } else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec)
        }
    }

    private fun measureHorizontal(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var wSize = View.MeasureSpec.getSize(widthMeasureSpec) - (paddingLeft + paddingRight)
        if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.UNSPECIFIED) {
            wSize = Integer.MAX_VALUE
        }
        val count = childCount
        var rowWidth = 0
        var totalHeight = 0
        var rowMaxHeight = 0
        var childWidth: Int
        var maxRowHeight = paddingTop + paddingBottom
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
                val layoutParams = child.layoutParams as FrameLayout.LayoutParams
                childWidth = child.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
                rowMaxHeight = Math.max(rowMaxHeight, child.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin)
                if (childWidth + rowWidth > wSize) {
                    totalHeight += rowMaxHeight
                    maxRowHeight = Math.max(maxRowHeight, rowWidth)
                    rowWidth = childWidth
                    rowMaxHeight = 0
                } else {
                    rowWidth += childWidth
                }
            }
        }
        if (rowWidth != 0) {
            maxRowHeight = Math.max(maxRowHeight, rowWidth)
            totalHeight += rowMaxHeight
        }
        if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.UNSPECIFIED) {
            wSize = maxRowHeight - (paddingLeft + paddingRight)
        }
        setMeasuredDimension(View.resolveSize(wSize, widthMeasureSpec), View.resolveSize(totalHeight + paddingTop + paddingBottom, heightMeasureSpec))
    }

    private fun measureVertical(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var hSize = View.MeasureSpec.getSize(heightMeasureSpec) - (paddingTop + paddingBottom)
        val count = childCount
        var columnHeight = 0
        var totalWidth = 0
        var maxColumnHeight = 0
        var columnMaxWidth = 0
        var childHeight: Int
        if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.UNSPECIFIED) {
            hSize = Integer.MAX_VALUE
        }
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
                val layoutParams = child.layoutParams as FrameLayout.LayoutParams
                childHeight = child.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin
                columnMaxWidth = Math.max(columnMaxWidth, child.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin)
                if (childHeight + columnHeight > hSize) {
                    totalWidth += columnMaxWidth
                    maxColumnHeight = Math.max(maxColumnHeight, columnHeight)
                    columnHeight = childHeight
                    columnMaxWidth = 0
                } else {
                    columnHeight += childHeight
                }
            }
        }
        if (columnHeight != 0) {
            maxColumnHeight = Math.max(maxColumnHeight, columnHeight)
            totalWidth += columnMaxWidth
        }
        if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.UNSPECIFIED) {
            hSize = maxColumnHeight - (paddingTop + paddingBottom)
        }
        setMeasuredDimension(View.resolveSize(totalWidth + paddingRight + paddingLeft, widthMeasureSpec), View.resolveSize(hSize, heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        positions.clear()
        if (orientation == VERTICAL) {
            layoutVertical(left, top, right, bottom)
        } else {
            layoutHorizontal(left, top, right, bottom)
        }
    }

    private fun layoutVertical(left: Int, top: Int, right: Int, bottom: Int) {
        if (childCount == 0) {
            return
        }
        val width = right - paddingLeft - left - paddingRight
        val height = bottom - paddingTop - top - paddingBottom
        var childTop = paddingTop
        var childLeft = paddingLeft
        var totalHorizontal = paddingLeft + paddingRight
        var totalVertical = 0
        var column = 0
        var maxChildWidth = 0
        for (i in 0 until childCount) {
            val viewGroup = getChildAt(i) ?: continue
            if (viewGroup.visibility != View.GONE) {
                if (viewGroup.measuredHeight == 0 || viewGroup.measuredWidth == 0) {
                    viewGroup.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST))
                }
                val layoutParams = viewGroup.layoutParams as FrameLayout.LayoutParams
                val childWidth = viewGroup.measuredWidth
                val childHeight = viewGroup.measuredHeight
                if (childTop + childHeight + layoutParams.topMargin + layoutParams.bottomMargin > height + paddingTop) {
                    updateChildPositionVertical(height, totalVertical, column, maxChildWidth)
                    childTop = paddingTop
                    childLeft += maxChildWidth
                    maxChildWidth = 0
                    column++
                    totalVertical = 0
                }
                childTop += layoutParams.topMargin
                positions.add(ViewPosition(childLeft, childTop, column))
                val currentWidth = childWidth + layoutParams.leftMargin + layoutParams.rightMargin
                if (maxChildWidth < currentWidth) {
                    maxChildWidth = currentWidth
                }
                childTop += childHeight + layoutParams.bottomMargin
                totalVertical += childHeight + layoutParams.topMargin + layoutParams.bottomMargin
            }
        }
        updateChildPositionVertical(height, totalVertical, column, maxChildWidth)
        totalHorizontal += childLeft + maxChildWidth
        updateChildPositionHorizontal(width, totalHorizontal, column, 0)
        // positions.clear();
    }

    private fun layoutHorizontal(left: Int, top: Int, right: Int, bottom: Int) {
        val count = childCount
        if (count == 0) {
            return
        }
        val width = right - paddingLeft - left - paddingRight
        val height = bottom - paddingTop - top - paddingBottom
        var childTop = paddingTop
        var childLeft = paddingLeft
        var totalHorizontal = 0
        var totalVertical = paddingTop + paddingBottom
        var row = 0
        var maxChildHeight = 0
        for (i in 0 until count) {
            val viewGroup = getChildAt(i) ?: continue
            if (viewGroup.visibility != View.GONE) {
                if (viewGroup.measuredHeight == 0 || viewGroup.measuredWidth == 0) {
                    viewGroup.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST))
                }
                val layoutParams = viewGroup.layoutParams as FrameLayout.LayoutParams
                val childWidth = viewGroup.measuredWidth
                val childHeight = viewGroup.measuredHeight
                if (childLeft + childWidth + layoutParams.leftMargin + layoutParams.rightMargin > width + paddingLeft) {
                    updateChildPositionHorizontal(width, totalHorizontal, row, maxChildHeight)
                    childLeft = paddingLeft
                    childTop += maxChildHeight
                    maxChildHeight = 0
                    row++
                    totalHorizontal = 0
                }
                childLeft += layoutParams.leftMargin
                positions.add(ViewPosition(childLeft, childTop, row))
                val currentHeight = childHeight + layoutParams.topMargin + layoutParams.bottomMargin
                if (maxChildHeight < currentHeight) {
                    maxChildHeight = currentHeight
                }
                childLeft += childWidth + layoutParams.rightMargin
                totalHorizontal += childWidth + layoutParams.rightMargin + layoutParams.leftMargin
            }
        }
        updateChildPositionHorizontal(width, totalHorizontal, row, maxChildHeight)
        totalVertical += childTop + maxChildHeight
        updateChildPositionVertical(height, totalVertical, row, 0)
        // positions.clear();
    }

    private fun updateChildPositionVertical(height: Int, totalSize: Int, column: Int, maxChildWidth: Int) {
        for (i in positions.indices) {
            val viewPosition = positions[i]
            val child = getChildAt(i)
            if (orientation == HORIZONTAL || viewPosition.position == column) {
                updateTopPositionByGravity(viewPosition, height - totalSize, gravity)
            }
            if (orientation == VERTICAL && viewPosition.position == column) {
                val layoutParams = child.layoutParams as FrameLayout.LayoutParams
                val size = maxChildWidth - child.measuredWidth - layoutParams.leftMargin - layoutParams.rightMargin
                updateLeftPositionByGravity(viewPosition, size, layoutParams.gravity)
            }
            if (orientation == HORIZONTAL) {
                layout(child, viewPosition)
            }
        }
    }

    private fun updateChildPositionHorizontal(width: Int, totalSize: Int, row: Int, maxChildHeight: Int) {
        for (i in positions.indices) {
            val viewPosition = positions[i]
            val child = getChildAt(i)
            if (orientation == VERTICAL || viewPosition.position == row) {
                updateLeftPositionByGravity(viewPosition, width - totalSize, gravity)
            }
            if (orientation == HORIZONTAL && viewPosition.position == row) {
                val layoutParams = child.layoutParams as FrameLayout.LayoutParams
                val size = maxChildHeight - child.measuredHeight - layoutParams.topMargin - layoutParams.bottomMargin
                updateTopPositionByGravity(viewPosition, size, layoutParams.gravity)
            }
            if (orientation == VERTICAL) {
                layout(child, viewPosition)
            }
        }
    }

    private fun updateLeftPositionByGravity(viewPosition: ViewPosition, size: Int, gravity: Int) {
        when (gravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
            Gravity.END -> viewPosition.left += if (size > 0) size else 0
            Gravity.CENTER_HORIZONTAL -> viewPosition.left += (if (size > 0) size else 0) / 2
        }
    }

    private fun updateTopPositionByGravity(viewPosition: ViewPosition, size: Int, gravity: Int) {
        when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
            Gravity.BOTTOM -> viewPosition.top += if (size > 0) size else 0
            Gravity.CENTER_VERTICAL -> viewPosition.top += (if (size > 0) size else 0) / 2
        }
    }

    private fun layout(child: View, viewPosition: ViewPosition) {
        val layoutParams = child.layoutParams as FrameLayout.LayoutParams
        if (orientation == HORIZONTAL) {
            child.layout(viewPosition.left,
                    viewPosition.top + layoutParams.topMargin,
                    viewPosition.left + child.measuredWidth,
                    viewPosition.top + child.measuredHeight + layoutParams.topMargin)
        } else {
            child.layout(viewPosition.left + layoutParams.leftMargin,
                    viewPosition.top,
                    viewPosition.left + child.measuredWidth + layoutParams.leftMargin,
                    viewPosition.top + child.measuredHeight)
        }
    }

    fun setGravity(gravity: Int) {
        if (this.gravity != gravity) {
            if (gravity.has(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK)) {
                this.gravity = gravity or GravityCompat.START
            }
            if (gravity.has(Gravity.VERTICAL_GRAVITY_MASK)) {
                this.gravity = gravity or Gravity.TOP
            }
            requestLayout()
        }
    }

    fun setHorizontalGravity(horizontalGravity: Int) {
        val gravity = horizontalGravity and GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK
        if (this.gravity.has(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK)) {
            this.gravity = this.gravity and GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK.inv() or gravity
            requestLayout()
        }
    }

    fun setVerticalGravity(verticalGravity: Int) {
        val gravity = verticalGravity and Gravity.VERTICAL_GRAVITY_MASK
        if (this.gravity.has(Gravity.VERTICAL_GRAVITY_MASK)) {
            this.gravity = this.gravity and Gravity.VERTICAL_GRAVITY_MASK.inv() or gravity
            requestLayout()
        }
    }

    internal class ViewPosition(var left: Int, var top: Int, val position: Int) {

        override fun toString(): String {
            return "left:$left|top:$top|position:$position"
        }

    }

}
