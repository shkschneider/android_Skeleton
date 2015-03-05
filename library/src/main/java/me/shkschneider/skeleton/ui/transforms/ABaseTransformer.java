package me.shkschneider.skeleton.ui.transforms;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public abstract class ABaseTransformer implements PageTransformer {

	protected abstract void onTransform(final View page, final float position);

	@Override
	public void transformPage(final View page, final float position) {
		onPreTransform(page, position);
		onTransform(page, position);
		onPostTransform(page, position);
	}

	protected boolean hideOffscreenPages() {
		return true;
	}

	protected boolean isPagingEnabled() {
		return false;
	}

	protected void onPreTransform(final View page, final float position) {
		final float width = page.getWidth();
		page.setRotationX(0);
		page.setRotationY(0);
		page.setRotation(0);
		page.setScaleX(1);
		page.setScaleY(1);
		page.setPivotX(0);
		page.setPivotY(0);
		page.setTranslationY(0);
		page.setTranslationX(isPagingEnabled() ? 0F : -width * position);
		if (hideOffscreenPages()) {
			page.setAlpha(position <= -1F || position >= 1F ? 0F : 1F);
		}
        else {
			page.setAlpha(1F);
		}
	}

	protected void onPostTransform(final View page, final float position) {
        // Empty
	}

	protected static float min(final float val, final float min) {
		return (val < min ? min : val);
	}

}
