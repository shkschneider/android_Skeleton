package me.shkschneider.skeleton.ui.transforms;

import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class FlipHorizontalTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(final View view, final float position) {
		final float rotation = 180F * position;
		view.setAlpha(rotation > 90F || rotation < -90F ? 0F : 1F);
		view.setPivotX(view.getWidth() * 0.5F);
		view.setPivotY(view.getHeight() * 0.5F);
		view.setRotationY(rotation);
	}

}
