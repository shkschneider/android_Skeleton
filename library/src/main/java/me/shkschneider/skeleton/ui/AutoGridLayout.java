package me.shkschneider.skeleton.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.helper.AndroidHelper;

// <https://github.com/AlbertGrobas/AutoLinearLayout>
public class AutoGridLayout extends FrameLayout {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int mOrientation;
    private int mGravity = (Gravity.TOP | Gravity.LEFT);

    private List<ViewPosition> mListPositions = new ArrayList<>();

    public AutoGridLayout(final Context context) {
        this(context, null);
    }

    public AutoGridLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoGridLayout(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    @SuppressWarnings("unused")
    @TargetApi(AndroidHelper.API_21)
    public AutoGridLayout(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(final AttributeSet attrs) {
        final int orientation = ((attrs != null) ? attrs.getAttributeResourceValue(ViewHelper.ANDROIDXML, "orientation", -1) : -1);
        if (orientation == HORIZONTAL) {
            setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        }
        else if (orientation == VERTICAL) {
            setVerticalGravity(Gravity.CENTER_VERTICAL);
        }
        else {
            setGravity(Gravity.CENTER);
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        if (mOrientation == VERTICAL) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        }
        else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void measureHorizontal(final int widthMeasureSpec, final int heightMeasureSpec) {
        int wSize = MeasureSpec.getSize(widthMeasureSpec) - (getPaddingLeft() + getPaddingRight());
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            wSize = Integer.MAX_VALUE;
        }
        final int count = getChildCount();
        int rowWidth = 0;
        int totalHeight = 0;
        int rowMaxHeight = 0;
        int childWidth;
        int maxRowHeight = getPaddingTop() + getPaddingBottom();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                rowMaxHeight = Math.max(rowMaxHeight, child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                if ((childWidth + rowWidth) > wSize) {
                    totalHeight += rowMaxHeight;
                    maxRowHeight = Math.max(maxRowHeight, rowWidth);
                    rowWidth = childWidth;
                    rowMaxHeight = 0;
                }
                else {
                    rowWidth += childWidth;
                }
            }
        }
        if (rowWidth != 0) {
            maxRowHeight = Math.max(maxRowHeight, rowWidth);
            totalHeight += rowMaxHeight;
        }
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            wSize = maxRowHeight - (getPaddingLeft() + getPaddingRight());
        }
        setMeasuredDimension(resolveSize(wSize, widthMeasureSpec), resolveSize(totalHeight + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
    }

    private void measureVertical(final int widthMeasureSpec, final int heightMeasureSpec) {
        int hSize = MeasureSpec.getSize(heightMeasureSpec) - (getPaddingTop() + getPaddingBottom());
        final int count = getChildCount();
        int columnHeight = 0;
        int totalWidth = 0, maxColumnHeight = 0;
        int columnMaxWidth = 0;
        int childHeight;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            hSize = Integer.MAX_VALUE;
        }
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                columnMaxWidth = Math.max(columnMaxWidth, child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                if ((childHeight + columnHeight) > hSize) {
                    totalWidth += columnMaxWidth;
                    maxColumnHeight = Math.max(maxColumnHeight, columnHeight);
                    columnHeight = childHeight;
                    columnMaxWidth = 0;
                }
                else {
                    columnHeight += childHeight;
                }
            }
        }
        if (columnHeight != 0) {
            maxColumnHeight = Math.max(maxColumnHeight, columnHeight);
            totalWidth += columnMaxWidth;
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            hSize = maxColumnHeight - (getPaddingTop() + getPaddingBottom());
        }
        setMeasuredDimension(resolveSize(totalWidth + getPaddingRight() + getPaddingLeft(), widthMeasureSpec), resolveSize(hSize, heightMeasureSpec));
    }

    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {
        mListPositions.clear();
        if (mOrientation == VERTICAL) {
            layoutVertical(left, top, right, bottom);
        }
        else {
            layoutHorizontal(left, top, right, bottom);
        }
    }

    private void layoutVertical(final int left, final int top, final int right, final int bottom) {
        final int count = getChildCount();
        if (count == 0) {
            return;
        }
        final int width = right - getPaddingLeft() - left - getPaddingRight();
        final int height = bottom - getPaddingTop() - top - getPaddingBottom();
        int childTop = getPaddingTop();
        int childLeft = getPaddingLeft();
        int totalHorizontal = getPaddingLeft() + getPaddingRight();
        int totalVertical = 0;
        int column = 0;
        int maxChildWidth = 0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if ((child != null) && (child.getVisibility() != View.GONE)) {
                if ((child.getMeasuredHeight() == 0) || (child.getMeasuredWidth() == 0)) {
                    child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
                }
                final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
                if ((childTop + childHeight + layoutParams.topMargin + layoutParams.bottomMargin) > (height + getPaddingTop())) {
                    updateChildPositionVertical(height, totalVertical, column, maxChildWidth);
                    childTop = getPaddingTop();
                    childLeft += maxChildWidth;
                    maxChildWidth = 0;
                    column++;
                    totalVertical = 0;
                }
                childTop += layoutParams.topMargin;
                mListPositions.add(new ViewPosition(childLeft, childTop, column));
                final int currentWidth = childWidth + layoutParams.leftMargin + layoutParams.rightMargin;
                if (maxChildWidth < currentWidth) {
                    maxChildWidth = currentWidth;
                }
                childTop += childHeight + layoutParams.bottomMargin;
                totalVertical += childHeight + layoutParams.topMargin + layoutParams.bottomMargin;
            }
        }
        updateChildPositionVertical(height, totalVertical, column, maxChildWidth);
        totalHorizontal += (childLeft + maxChildWidth);
        updateChildPositionHorizontal(width, totalHorizontal, column, 0);
        // mListPositions.clear();
    }

    private void layoutHorizontal(final int left, final int top, final int right, final int bottom) {
        final int count = getChildCount();
        if (count == 0) {
            return;
        }
        final int width = right - getPaddingLeft() - left - getPaddingRight();
        final int height = bottom - getPaddingTop() - top - getPaddingBottom();
        int childTop = getPaddingTop();
        int childLeft = getPaddingLeft();
        int totalHorizontal = 0;
        int totalVertical = getPaddingTop() + getPaddingBottom();
        int row = 0;
        int maxChildHeight = 0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if ((child != null) && (child.getVisibility() != View.GONE)) {
                if ((child.getMeasuredHeight() == 0) || (child.getMeasuredWidth() == 0)) {
                    child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
                }
                final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
                if ((childLeft + childWidth + layoutParams.leftMargin + layoutParams.rightMargin) > (width + getPaddingLeft())) {
                    updateChildPositionHorizontal(width, totalHorizontal, row, maxChildHeight);
                    childLeft = getPaddingLeft();
                    childTop += maxChildHeight;
                    maxChildHeight = 0;
                    row++;
                    totalHorizontal = 0;
                }
                childLeft += layoutParams.leftMargin;
                mListPositions.add(new ViewPosition(childLeft, childTop, row));
                int currentHeight = childHeight + layoutParams.topMargin + layoutParams.bottomMargin;
                if (maxChildHeight < currentHeight) {
                    maxChildHeight = currentHeight;
                }
                childLeft += (childWidth + layoutParams.rightMargin);
                totalHorizontal += (childWidth + layoutParams.rightMargin + layoutParams.leftMargin);
            }
        }
        updateChildPositionHorizontal(width, totalHorizontal, row, maxChildHeight);
        totalVertical += (childTop + maxChildHeight);
        updateChildPositionVertical(height, totalVertical, row, 0);
        // mListPositions.clear();
    }

    private void updateChildPositionVertical(final int height, final int totalSize, final int column, final int maxChildWidth) {
        for (int i = 0; i < mListPositions.size(); i++) {
            final ViewPosition viewPosition = mListPositions.get(i);
            final View child = getChildAt(i);
            if ((mOrientation == HORIZONTAL) || (viewPosition.position == column)) {
                updateTopPositionByGravity(viewPosition, height - totalSize, mGravity);
            }
            if ((mOrientation == VERTICAL) && (viewPosition.position == column)) {
                final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                final int size = maxChildWidth - child.getMeasuredWidth() - layoutParams.leftMargin - layoutParams.rightMargin;
                updateLeftPositionByGravity(viewPosition, size, layoutParams.gravity);
            }
            if (mOrientation == HORIZONTAL) {
                layout(child, viewPosition);
            }
        }
    }

    private void updateChildPositionHorizontal(final int width, final int totalSize, final int row, final int maxChildHeight) {
        for (int i = 0; i < mListPositions.size(); i++) {
            final ViewPosition viewPosition = mListPositions.get(i);
            final View child = getChildAt(i);
            if ((mOrientation == VERTICAL) || (viewPosition.position == row)) {
                updateLeftPositionByGravity(viewPosition, width - totalSize, mGravity);
            }
            if ((mOrientation == HORIZONTAL) && (viewPosition.position == row)) {
                final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                final int size = maxChildHeight - child.getMeasuredHeight() - layoutParams.topMargin - layoutParams.bottomMargin;
                updateTopPositionByGravity(viewPosition, size, layoutParams.gravity);
            }
            if (mOrientation == VERTICAL) {
                layout(child, viewPosition);
            }
        }
    }

    private void updateLeftPositionByGravity(final ViewPosition viewPosition, final int size, final int gravity) {
        switch ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK)) {
            case Gravity.RIGHT:
                viewPosition.left += ((size > 0) ? size : 0);
                break ;
            case Gravity.CENTER_HORIZONTAL:
                viewPosition.left += (((size > 0) ? size : 0) / 2);
                break ;
        }
    }

    private void updateTopPositionByGravity(final ViewPosition viewPosition, final int size, final int gravity) {
        switch ((gravity & Gravity.VERTICAL_GRAVITY_MASK)) {
            case Gravity.BOTTOM:
                viewPosition.top += ((size > 0) ? size : 0);
                break ;
            case Gravity.CENTER_VERTICAL:
                viewPosition.top += (((size > 0) ? size : 0) / 2);
                break ;
        }
    }

    private void layout(final View child, final ViewPosition viewPosition) {
        final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        if (mOrientation == HORIZONTAL) {
            child.layout(viewPosition.left,
                    viewPosition.top + layoutParams.topMargin,
                    viewPosition.left + child.getMeasuredWidth(),
                    viewPosition.top + child.getMeasuredHeight() + layoutParams.topMargin);
        }
        else {
            child.layout(viewPosition.left + layoutParams.leftMargin,
                    viewPosition.top,
                    viewPosition.left + child.getMeasuredWidth() + layoutParams.leftMargin,
                    viewPosition.top + child.getMeasuredHeight());
        }
    }

    public void setGravity(int gravity) {
        if (mGravity != gravity) {
            if ((gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
                gravity |= GravityCompat.START;
            }
            if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == 0) {
                gravity |= Gravity.TOP;
            }
            mGravity = gravity;
            requestLayout();
        }
    }

    public void setHorizontalGravity(final int horizontalGravity) {
        final int gravity = (horizontalGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK);
        if ((mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) != gravity) {
            mGravity = ((mGravity & ~GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) | gravity);
            requestLayout();
        }
    }

    public void setVerticalGravity(final int verticalGravity) {
        final int gravity = (verticalGravity & Gravity.VERTICAL_GRAVITY_MASK);
        if ((mGravity & Gravity.VERTICAL_GRAVITY_MASK) != gravity) {
            mGravity = ((mGravity & ~Gravity.VERTICAL_GRAVITY_MASK) | gravity);
            requestLayout();
        }
    }

    public void setOrientation(final int orientation) {
        if ((orientation != HORIZONTAL) && (orientation != VERTICAL)) {
            return;
        }
        if (mOrientation != orientation) {
            mOrientation = orientation;
            requestLayout();
        }
    }

    public int getOrientation() {
        return mOrientation;
    }

    static class ViewPosition {

        int left;
        int top;
        int position; //row or column

        public ViewPosition(final int left, final int top, final int position) {
            this.left = left;
            this.top = top;
            this.position = position;
        }

        @Override
        public String toString() {
            return "left:" + left + "|top:" + top + "|position:" + position;
        }

    }

}
