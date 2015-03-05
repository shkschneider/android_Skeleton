package me.shkschneider.skeleton.ui.transforms;

import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class CubeOutTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(final View view, final float position) {
		view.setPivotX(position < 0F ? view.getWidth() : 0F);
		view.setPivotY(view.getHeight() * 0.5F);
		view.setRotationY(90F * position);
	}

	@Override
	public boolean isPagingEnabled() {
		return true;
	}

}
