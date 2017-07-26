package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

// <https://github.com/google/iosched>
public class MySlidingViewPagerIndicator extends HorizontalScrollView {

    private static final int DEFAULT_TEXTCOLOR = Color.BLACK;
    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;

    private final SlidingTabStrip mSlidingTabStrib;
    private final int mTitleOffset;

    private Colorizer mColorizer;
    private boolean mDistributeEvenly;
    private ViewPager mViewPager;

    public MySlidingViewPagerIndicator(final Context context) {
        this(context, null);
    }

    public MySlidingViewPagerIndicator(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySlidingViewPagerIndicator(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        mTitleOffset = (int) (TITLE_OFFSET_DIPS * Resources.getSystem().getDisplayMetrics().density);
        mSlidingTabStrib = new SlidingTabStrip(context);
        addView(mSlidingTabStrib, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public void setColorizer(final Colorizer colorizer) {
        mColorizer = colorizer;
        mSlidingTabStrib.setColorizer(mColorizer);
    }

    public void setDistributeEvenly(final boolean distributeEvenly) {
        mDistributeEvenly = distributeEvenly;
    }

    public void setViewPager(final ViewPager viewPager) {
        mSlidingTabStrib.removeAllViews();
        mViewPager = viewPager;
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(new InternalViewPagerListener());
            final PagerAdapter pagerAdapter = mViewPager.getAdapter();
            final OnClickListener onClickListener = new TabClickListener();
            for (int i = 0; i < pagerAdapter.getCount(); i++) {
                final View view = tabView(getContext());
                if (mDistributeEvenly) {
                    final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.width = 0;
                    layoutParams.weight = 1;
                }
                if (TextView.class.isInstance(view)) {
                    final TextView textView = (TextView) view;
                    textView.setTextColor((mColorizer == null) ? DEFAULT_TEXTCOLOR : mColorizer.getTextColor(i));
                    textView.setText(pagerAdapter.getPageTitle(i));
                }
                view.setOnClickListener(onClickListener);
                mSlidingTabStrib.addView(view);
                view.setSelected(i == mViewPager.getCurrentItem());
            }
        }
    }

    protected TextView tabView(final Context context) {
        final TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        textView.setTextColor(DEFAULT_TEXTCOLOR);
        textView.setBackgroundResource(typedValue.resourceId);
        textView.setAllCaps(true);
        final int padding = (int) (TAB_VIEW_PADDING_DIPS * Resources.getSystem().getDisplayMetrics().density);
        textView.setPadding(padding, padding, padding, padding);
        return textView;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mViewPager != null) {
            scroll(mViewPager.getCurrentItem(), 0);
        }
    }

    private void scroll(final int tab, final int offset) {
        final int childCount = mSlidingTabStrib.getChildCount();
        if (childCount == 0 || tab < 0 || tab >= childCount) {
            return;
        }
        final View child = mSlidingTabStrib.getChildAt(tab);
        if (child != null) {
            int scroll = child.getLeft() + offset;
            if (tab > 0 || offset > 0) {
                scroll -= mTitleOffset;
            }
            scrollTo(scroll, 0);
        }
    }

    public interface Colorizer {

        @ColorInt
        int getTextColor(final int position);

        @ColorInt
        int getIndicatorColor(final int position);

        @ColorInt
        int getDividerColor(final int position);

    }

    private class TabClickListener implements OnClickListener {

        @Override
        public void onClick(final View view) {
            for (int i = 0; i < mSlidingTabStrib.getChildCount(); i++) {
                if (view == mSlidingTabStrib.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }

    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {

        private int mScrollState;

        @Override
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            final int childCount = mSlidingTabStrib.getChildCount();
            if ((childCount == 0) || (position < 0) || (position >= childCount)) {
                return;
            }
            mSlidingTabStrib.onViewPagerPageChanged(position, positionOffset);
            final View child = mSlidingTabStrib.getChildAt(position);
            final int offset = ((child != null) ? (int) (positionOffset * child.getWidth()) : 0);
            scroll(position, offset);
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            mScrollState = state;
        }

        @Override
        public void onPageSelected(final int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mSlidingTabStrib.onViewPagerPageChanged(position, 0F);
                scroll(position, 0);
            }
            for (int i = 0; i < mSlidingTabStrib.getChildCount(); i++) {
                mSlidingTabStrib.getChildAt(i).setSelected(position == i);
            }
        }

    }

    private static class SlidingTabStrip extends LinearLayout {

        private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;
        private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
        private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 4;
        private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;
        private static final float DEFAULT_DIVIDER_HEIGHT = 0.5F;

        private final int mBottomBorderThickness;
        private final Paint mBottomBorderPaint;
        private final int mSelectedIndicatorThickness;
        private final Paint mSelectedIndicatorPaint;
        private final float mDividerHeight;
        private final Paint mDividerPaint;

        private int mSelectedPosition;
        private float mSelectionOffset;
        private Colorizer mColorizer;

        public SlidingTabStrip(final Context context) {
            this(context, null);
        }

        public SlidingTabStrip(final Context context, final AttributeSet attrs) {
            super(context, attrs);
            setWillNotDraw(false);
            final float density = Resources.getSystem().getDisplayMetrics().density;
            final TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.colorForeground, typedValue, true);
            mBottomBorderThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
            mBottomBorderPaint = new Paint();
            mBottomBorderPaint.setColor(SlidingTabStrip.setColorAlpha(typedValue.data, DEFAULT_BOTTOM_BORDER_COLOR_ALPHA));
            mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
            mSelectedIndicatorPaint = new Paint();
            mDividerHeight = DEFAULT_DIVIDER_HEIGHT;
            mDividerPaint = new Paint();
            mDividerPaint.setStrokeWidth((int) (DEFAULT_DIVIDER_THICKNESS_DIPS * density));
        }

        void setColorizer(final Colorizer colorizer) {
            mColorizer = colorizer;
            invalidate();
        }

        void onViewPagerPageChanged(final int position, final float positionOffset) {
            mSelectedPosition = position;
            mSelectionOffset = positionOffset;
            invalidate();
        }

        @Override
        protected void onDraw(final Canvas canvas) {
            final int height = getHeight();
            final int childCount = getChildCount();
            final int dividerHeight = (int) (Math.min(Math.max(0F, mDividerHeight), 1F) * height);
            if (childCount > 0) {
                View selectedTitle = getChildAt(mSelectedPosition);
                int left = selectedTitle.getLeft();
                int right = selectedTitle.getRight();
                int color = mColorizer.getIndicatorColor(mSelectedPosition);
                if (mSelectionOffset > 0F && mSelectedPosition < (getChildCount() - 1)) {
                    final int nextColor = mColorizer.getIndicatorColor(mSelectedPosition + 1);
                    if (color != nextColor) {
                        color = blendColors(nextColor, color, mSelectionOffset);
                    }
                    final View view = getChildAt(mSelectedPosition + 1);
                    left = (int) (mSelectionOffset * view.getLeft() + (1.0F - mSelectionOffset) * left);
                    right = (int) (mSelectionOffset * view.getRight() + (1.0F - mSelectionOffset) * right);
                }
                mSelectedIndicatorPaint.setColor(color);
                canvas.drawRect(left, height - mSelectedIndicatorThickness, right, height, mSelectedIndicatorPaint);
            }
            canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height, mBottomBorderPaint);
            final int divider = (height - dividerHeight) / 2;
            for (int i = 0; i < childCount - 1; i++) {
                final View child = getChildAt(i);
                mDividerPaint.setColor(mColorizer.getDividerColor(i));
                canvas.drawLine(child.getRight(), divider, child.getRight(), divider + dividerHeight, mDividerPaint);
            }
        }

        private static int setColorAlpha(final int color, final byte alpha) {
            return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
        }

        private static int blendColors(final int color1, final int color2, final float ratio) {
            final float inverseRation = 1F - ratio;
            final float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
            final float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
            final float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
            return Color.rgb((int) r, (int) g, (int) b);
        }

    }

}
