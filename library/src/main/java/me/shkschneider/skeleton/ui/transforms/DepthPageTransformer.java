package me.shkschneider.skeleton.ui.transforms;

import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class DepthPageTransformer extends ABaseTransformer {

	private static final float MIN_SCALE = 0.75F;

	@Override
	protected void onTransform(final View view, final float position) {
		if (position <= 0F) {
			view.setTranslationX(0F);
			view.setScaleX(1F);
			view.setScaleY(1F);
		}
        else if (position <= 1F) {
			final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
			view.setAlpha(1 - position);
			view.setPivotY(0.5F * view.getHeight());
			view.setTranslationX(view.getWidth() * -position);
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);
		}
	}

	@Override
	protected boolean isPagingEnabled() {
		return true;
	}

}
