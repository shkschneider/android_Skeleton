package me.shkschneider.skeleton.ui.transforms;

import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class RotateUpTransformer extends ABaseTransformer {

	private static final float ROT_MOD = -15F;

	@Override
	protected void onTransform(final View view, final float position) {
		final float width = view.getWidth();
		final float rotation = ROT_MOD * position;
		view.setPivotX(width * 0.5F);
		view.setPivotY(0F);
		view.setTranslationX(0F);
		view.setRotation(rotation);
	}
	
	@Override
	protected boolean isPagingEnabled() {
		return true;
	}

}
