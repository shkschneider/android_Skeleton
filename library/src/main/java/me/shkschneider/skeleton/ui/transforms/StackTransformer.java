package me.shkschneider.skeleton.ui.transforms;

import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class StackTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(final View view, final float position) {
		view.setTranslationX(position < 0 ? 0F : -view.getWidth() * position);
	}

}
