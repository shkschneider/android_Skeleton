package me.shkschneider.skeleton.ui;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * ViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
 *
 * @see <http://developer.android.com/training/animation/screen-slide.html#pagetransformer>
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85F;
    private static final float MIN_ALPHA = 0.5F;

    @Override
    public void transformPage(final View view, final float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        // -infinity -1
        if (position < -1) {
            view.setAlpha(0);
        }
        // -1 +1
        else if (position <= 1) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            }
            else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        }
        // +1 +infinity
        else {
            view.setAlpha(0);
        }
    }

}
