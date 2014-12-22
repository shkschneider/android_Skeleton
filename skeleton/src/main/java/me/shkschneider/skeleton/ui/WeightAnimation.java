package me.shkschneider.skeleton.ui;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

public class WeightAnimation extends Animation {

    private View mView;
    private float mStartWeight;
    private float mStopWeight;

    public WeightAnimation(final View view, final float startWeight, final float endWeight) {
        mView = view;
        mStartWeight = startWeight;
        mStopWeight = endWeight - startWeight;
    }

    @Override
    protected void applyTransformation(final float interpolatedTime, final Transformation transformation) {
        final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mView.getLayoutParams();
        if (layoutParams == null) {
            return ;
        }

        layoutParams.weight = (mStartWeight + (mStopWeight * interpolatedTime));
        mView.setLayoutParams(layoutParams);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

    public void animate(final long duration, final AnimationListener animationListener) {
        setDuration(duration);
        setAnimationListener(animationListener);
        mView.startAnimation(this);
    }

    public void animate(final long duration) {
        animate(duration, null);
    }

}