package me.shkschneider.skeleton.ui;

/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import me.shkschneider.app.R;

public class ActionBarPagerLineStrip extends HorizontalScrollView {

    private boolean mExpand;
    private int mTitleOffset;
    private int mLayoutId;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    private final ActionBarTabStripCell mTabStripCell;

    public ActionBarPagerLineStrip(final Context context) {
        this(context, null);
    }

    public ActionBarPagerLineStrip(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionBarPagerLineStrip(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        mExpand = true;
        mTitleOffset = (int) (24 * getResources().getDisplayMetrics().density);
        mTabStripCell = new ActionBarTabStripCell(context);
        addView(mTabStripCell, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setBackgroundColor(getResources().getColor(R.color.actionBarColor));
    }

    public void setOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }

    public void setCustomTabView(final int layoutResId) {
        mLayoutId = layoutResId;
    }

    public void setViewPager(final ViewPager viewPager) {
        mTabStripCell.removeAllViews();
        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateLineStrip();
        }
    }

    public void setExpand(final boolean expand) {
        mExpand = expand;
    }

    protected View createDefaultLineView(final Context context) {
        final View view = new View(context);
        final int padding = (int) (16 * getResources().getDisplayMetrics().density);
        view.setPadding(padding, padding, padding, padding);
        return view;
    }

    private void populateLineStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final OnClickListener tabClickListener = new TabClickListener();
        for (int i = 0; i < adapter.getCount(); i++) {
            View lineView = null;
            if (mLayoutId != 0) {
                lineView = LayoutInflater.from(getContext()).inflate(mLayoutId, mTabStripCell, false);
            }
            if (lineView == null) {
                lineView = createDefaultLineView(getContext());
            }
            if (mExpand) {
                lineView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F));
            }
            lineView.setOnClickListener(tabClickListener);
            mTabStripCell.addView(lineView);
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
        final int tabStripChildCount = mTabStripCell.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return ;
        }
        final View selectedChild = mTabStripCell.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;
            if (tabIndex > 0 || positionOffset > 0) {
                targetScrollX -= mTitleOffset;
            }
            scrollTo(targetScrollX, 0);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {

        private int mScrollState;

        @Override
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            final int tabStripChildCount = mTabStripCell.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return ;
            }
            mTabStripCell.onViewPagerPageChanged(position, positionOffset);
            final View selectedTitle = mTabStripCell.getChildAt(position);
            final int extraOffset = ((selectedTitle != null) ? (int) (positionOffset * selectedTitle.getWidth()) : 0);
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

    private class TabClickListener implements OnClickListener {

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

    private class ActionBarTabStripCell extends LinearLayout {

        private int mPosition;
        private float mPositionOffset;
        private final int mIndicatorThickness;
        private final Paint mIndicatorPaint;

        public ActionBarTabStripCell(final Context context) {
            this(context, null);
        }

        public ActionBarTabStripCell(final Context context, final AttributeSet attrs) {
            super(context, attrs);
            setWillNotDraw(false);
            final float density = getResources().getDisplayMetrics().density;
            mIndicatorThickness = (int) (8 * density);
            mIndicatorPaint = new Paint();
            mIndicatorPaint.setColor(getResources().getColor(R.color.white));
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
                final View selectedTitle = getChildAt(mPosition);
                int left = selectedTitle.getLeft();
                int right = selectedTitle.getRight();
                final int color = mIndicatorPaint.getColor();
                if (mPositionOffset > 0F && mPosition < (getChildCount() - 1)) {
                    final View nextTitle = getChildAt(mPosition + 1);
                    left = (int) (mPositionOffset * nextTitle.getLeft() + (1.0F - mPositionOffset) * left);
                    right = (int) (mPositionOffset * nextTitle.getRight() + (1.0F - mPositionOffset) * right);
                }
                mIndicatorPaint.setColor(color);
                canvas.drawRect(left, height - mIndicatorThickness, right, height, mIndicatorPaint);
            }
        }

    }

}
