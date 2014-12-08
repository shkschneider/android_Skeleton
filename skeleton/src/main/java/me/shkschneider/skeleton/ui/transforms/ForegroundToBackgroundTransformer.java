package me.shkschneider.skeleton.ui.transforms;

import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class ForegroundToBackgroundTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(final View view, final float position) {
		final float height = view.getHeight();
		final float width = view.getWidth();
		final float scale = min(position > 0 ? 1F : Math.abs(1F + position), 0.5F);
		view.setScaleX(scale);
		view.setScaleY(scale);
		view.setPivotX(width * 0.5F);
		view.setPivotY(height * 0.5F);
		view.setTranslationX(position > 0 ? width * position : -width * position * 0.25F);
	}

}
