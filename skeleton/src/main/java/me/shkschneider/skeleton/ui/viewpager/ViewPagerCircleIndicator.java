package me.shkschneider.skeleton.ui.viewpager;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.ScreenHelper;

// <https://github.com/ongakuer/CircleIndicator>
public class ViewPagerCircleIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;

    private ViewPager mViewpager;
    private ViewPager.OnPageChangeListener mViewPagerOnPageChangeListener;
    private int mIndicatorMargin = ScreenHelper.pixelsFromDp(DEFAULT_INDICATOR_WIDTH);
    private int mIndicatorWidth = ScreenHelper.pixelsFromDp(DEFAULT_INDICATOR_WIDTH);
    private int mIndicatorHeight = ScreenHelper.pixelsFromDp(DEFAULT_INDICATOR_WIDTH);
    private int mAnimatorResId = R.animator.viewpager_circleindicator_scalealpha;
    private int mIndicatorBackground = R.drawable.viewpager_circleindicator;
    private int mCurrentPosition = 0;
    private AnimatorSet mAnimationOut;
    private AnimatorSet mAnimationIn;

    public ViewPagerCircleIndicator(final Context context) {
        this(context, null);
    }

    public ViewPagerCircleIndicator(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        mAnimationOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, mAnimatorResId);
        mAnimationOut.setInterpolator(new LinearInterpolator());
        mAnimationIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, mAnimatorResId);
        mAnimationIn.setInterpolator(new ReverseInterpolator());
    }

    public void setViewPager(final ViewPager viewPager) {
        mViewpager = viewPager;
        createIndicators(viewPager);
        mViewpager.setOnPageChangeListener(this);
    }

    public void setIndicator(final int res) {
        mIndicatorBackground = res;
        createIndicators(mViewpager);
    }

    private void createIndicators(final ViewPager viewPager) {
        if (viewPager == null) {
            LogHelper.warning("ViewPager was NULL");
            return ;
        }

        removeAllViews();
        final int count = viewPager.getAdapter().getCount();
        if (count <= 0) {
            return ;
        }
        for (int i = 0; i < count; i++) {
            final View indicator = new View(getContext());
            indicator.setBackgroundResource(mIndicatorBackground);
            addView(indicator, mIndicatorWidth, mIndicatorHeight);
            final LayoutParams layoutParams = (LayoutParams) indicator.getLayoutParams();
            layoutParams.leftMargin = mIndicatorMargin;
            layoutParams.rightMargin = mIndicatorMargin;
            indicator.setLayoutParams(layoutParams);
            mAnimationOut.setTarget(indicator);
            mAnimationOut.start();
        }
        mAnimationOut.setTarget(getChildAt(mCurrentPosition));
        mAnimationOut.start();
    }

    public void setOnPageChangeListener(final ViewPager.OnPageChangeListener onPageChangeListener) {
        if (mViewpager == null) {
            LogHelper.warning("ViewPager was NULL");
            return ;
        }
        mViewPagerOnPageChangeListener = onPageChangeListener;
        mViewpager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(final int position) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageSelected(position);
        }
        mAnimationIn.setTarget(getChildAt(mCurrentPosition));
        mAnimationIn.start();
        mAnimationOut.setTarget(getChildAt(position));
        mAnimationOut.start();
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private class ReverseInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0F - value);
        }

    }

}
