package me.shkschneider.skeleton.ui.transforms;

import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class CubeInTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(final View view, final float position) {
		view.setPivotX(position > 0 ? 0 : view.getWidth());
		view.setPivotY(0);
		view.setRotationY(-90F * position);
	}

	@Override
	public boolean isPagingEnabled() {
		return true;
	}

}
