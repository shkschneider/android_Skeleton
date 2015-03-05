package me.shkschneider.skeleton.ui.transforms;

import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class DefaultTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(final View view, final float position) {
        // Empty
	}

	@Override
	public boolean isPagingEnabled() {
		return true;
	}

}
