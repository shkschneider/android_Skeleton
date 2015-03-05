package me.shkschneider.skeleton.ui.transforms;

import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class ZoomInTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(final View view, final float position) {
		final float scale = position < 0 ? position + 1F : Math.abs(1F - position);
		view.setScaleX(scale);
		view.setScaleY(scale);
		view.setPivotX(view.getWidth() * 0.5F);
		view.setPivotY(view.getHeight() * 0.5F);
		view.setAlpha(position < -1F || position > 1F ? 0F : 1F - (scale - 1F));
	}

}
