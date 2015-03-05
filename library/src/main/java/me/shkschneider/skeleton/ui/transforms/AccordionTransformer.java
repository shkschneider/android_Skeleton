package me.shkschneider.skeleton.ui.transforms;

import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class AccordionTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(final View view, final float position) {
		view.setPivotX(position < 0 ? 0 : view.getWidth());
		view.setScaleX(position < 0 ? 1F + position : 1F - position);
	}

}
