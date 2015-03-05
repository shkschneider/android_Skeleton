package me.shkschneider.skeleton.ui.transforms;

import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class RotateDownTransformer extends ABaseTransformer {

	private static final float ROT_MOD = -15F;

	@Override
	protected void onTransform(final View view, final float position) {
		final float width = view.getWidth();
		final float height = view.getHeight();
		final float rotation = ROT_MOD * position * -1.25F;
		view.setPivotX(width * 0.5F);
		view.setPivotY(height);
		view.setRotation(rotation);
	}
	
	@Override
	protected boolean isPagingEnabled() {
		return true;
	}

}
