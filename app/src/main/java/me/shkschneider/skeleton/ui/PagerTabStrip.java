package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
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

import me.shkschneider.app.R;
import me.shkschneider.skeleton.helper.ScreenHelper;

public class PagerTabStrip extends HorizontalScrollView {

    private int mBackgroundColor;
    private int mForegroundColor;
    private int mIndicatorHeight;

    protected ViewPager mViewPager;
    protected ViewPager.OnPageChangeListener mViewPagerPageChangeListener;
    protected TabStripCell mTabStripCell;

    public PagerTabStrip(final Context context) {
        this(context, null);
    }

    public PagerTabStrip(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerTabStrip(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init(context, R.color.contentBackgroundColor, R.color.primaryColor, 4);
    }

    protected void init(final Context context, final int backgroundColor, final int foregroundColor, final int indicatorHeight) {
        removeAllViews();
        mBackgroundColor = backgroundColor;
        mForegroundColor = foregroundColor;
        mIndicatorHeight = indicatorHeight;
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        mTabStripCell = new TabStripCell(context);
        addView(mTabStripCell, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setBackgroundColor(getResources().getColor(mBackgroundColor));
    }

    public void setOnPageChangeListener(final ViewPager.OnPageChangeListener onPageChangeListener) {
        mViewPagerPageChangeListener = onPageChangeListener;
    }

    public void setViewPager(final ViewPager viewPager) {
        mTabStripCell.removeAllViews();
        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateStrip();
        }
    }

    private TextView createDefaultStrip(final Context context) {
        final TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        final TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        textView.setBackgroundResource(typedValue.resourceId);
        textView.setAllCaps(true);
        final int padding = (int) (16 * ScreenHelper.density());
        textView.setPadding(padding, padding, padding, padding);
        textView.setTextColor(getResources().getColor(mForegroundColor));
        return textView;
    }

    protected void populateStrip() {
        final PagerAdapter pagerAdapter = mViewPager.getAdapter();
        final View.OnClickListener onClickListener = new TabClickListener();
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            final View tabView = createDefaultStrip(getContext());
            ((TextView) tabView).setText(pagerAdapter.getPageTitle(i));
            tabView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F));
            tabView.setOnClickListener(onClickListener);
            mTabStripCell.addView(tabView);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mViewPager != null) {
            scrollToTab(mViewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(final int tabIndex, final int positionOffset) {
        final int childCount = mTabStripCell.getChildCount();
        if (childCount == 0 || tabIndex < 0 || tabIndex >= childCount) {
            return ;
        }
        final View childView = mTabStripCell.getChildAt(tabIndex);
        if (childView != null) {
            int targetScrollX = childView.getLeft() + positionOffset;
            if (tabIndex > 0 || positionOffset > 0) {
                targetScrollX -= (ScreenHelper.width() / 2) - (childView.getWidth() / 2);
            }
            scrollTo(targetScrollX, 0);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {

        private int mScrollState;

        @Override
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            final int childCount = mTabStripCell.getChildCount();
            if ((childCount == 0) || (position < 0) || (position >= childCount)) {
                return ;
            }
            mTabStripCell.onViewPagerPageChanged(position, positionOffset);
            final View tabStripCell = mTabStripCell.getChildAt(position);
            final int extraOffset = ((tabStripCell != null) ? (int) (positionOffset * tabStripCell.getWidth()) : 0);
            scrollToTab(position, extraOffset);
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            mScrollState = state;
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(final int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStripCell.onViewPagerPageChanged(position, 0F);
                scrollToTab(position, 0);
            }
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    protected class TabClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View view) {
            for (int i = 0; i < mTabStripCell.getChildCount(); i++) {
                if (view == mTabStripCell.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return ;
                }
            }
        }

    }

    protected class TabStripCell extends LinearLayout {

        private int mPosition;
        private float mPositionOffset;
        private final int mIndicatorThickness;
        private final Paint mIndicatorPaint;

        public TabStripCell(final Context context) {
            this(context, null);
        }

        public TabStripCell(final Context context, final AttributeSet attrs) {
            super(context, attrs);
            setWillNotDraw(false);
            mIndicatorThickness = (int) (mIndicatorHeight * ScreenHelper.density());
            mIndicatorPaint = new Paint();
            mIndicatorPaint.setColor(getResources().getColor(mForegroundColor));
            mPosition = 0;
            mPositionOffset = 0;
        }

        void onViewPagerPageChanged(final int position, final float positionOffset) {
            mPosition = position;
            mPositionOffset = positionOffset;
            invalidate();
        }

        @Override
        protected void onDraw(final Canvas canvas) {
            final int height = getHeight();
            if (getChildCount() > 0) {
                final View currentView = getChildAt(mPosition);
                int left = currentView.getLeft();
                int right = currentView.getRight();
                if (mPositionOffset > 0F && mPosition < (getChildCount() - 1)) {
                    final View nextView = getChildAt(mPosition + 1);
                    left = (int) (mPositionOffset * nextView.getLeft() + (1.0F - mPositionOffset) * left);
                    right = (int) (mPositionOffset * nextView.getRight() + (1.0F - mPositionOffset) * right);
                }
                mIndicatorPaint.setColor(mIndicatorPaint.getColor());
                canvas.drawRect(left, height - mIndicatorThickness, right, height, mIndicatorPaint);
            }
        }

    }

}
