package me.shkschneider.skeleton.ui.transforms;

import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class ZoomOutSlideTransformer extends ABaseTransformer {

	private static final float MIN_SCALE = 0.85F;
	private static final float MIN_ALPHA = 0.5F;

	@Override
	protected void onTransform(final View view, final float position) {
		if (position >= -1 || position <= 1) {
			final float height = view.getHeight();
			final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
			final float vertMargin = height * (1 - scaleFactor) / 2;
			final float horzMargin = view.getWidth() * (1 - scaleFactor) / 2;
			view.setPivotY(0.5F * height);
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
	}

}
