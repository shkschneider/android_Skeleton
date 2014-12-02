package me.shkschneider.skeleton.ui;

import android.support.v4.view.ViewPager;
import android.view.View;

// <http://developer.android.com/training/animation/screen-slide.html#pagetransformer>
public class DepthPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75F;

    @Override
    public void transformPage(final View view, final float position) {
        int pageWidth = view.getWidth();

        // -infinity -1
        if (position < -1) {
            view.setAlpha(0);
        }
        // -1 0
        else if (position <= 0) {
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);
        }
        // 0 +1
        else if (position <= 1) {
            view.setAlpha(1 - position);
            view.setTranslationX(pageWidth * -position);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        }
        // +1 +infinity
        else {
            view.setAlpha(0);
        }
    }

}
