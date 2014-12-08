package me.shkschneider.skeleton.ui.transforms;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;

// <https://github.com/ToxicBakery/ViewPagerTransforms>
public class TabletTransformer extends ABaseTransformer {

	private static final Matrix OFFSET_MATRIX = new Matrix();
	private static final Camera OFFSET_CAMERA = new Camera();
	private static final float[] OFFSET_TEMP_FLOAT = new float[2];

	@Override
	protected void onTransform(View view, float position) {
		final float rotation = (position < 0 ? 30F : -30F) * Math.abs(position);
		view.setTranslationX(getOffsetXForRotation(rotation, view.getWidth(), view.getHeight()));
		view.setPivotX(view.getWidth() * 0.5F);
		view.setPivotY(0);
		view.setRotationY(rotation);
	}

	protected static float getOffsetXForRotation(final float degrees, final int width, final int height) {
		OFFSET_MATRIX.reset();
		OFFSET_CAMERA.save();
		OFFSET_CAMERA.rotateY(Math.abs(degrees));
		OFFSET_CAMERA.getMatrix(OFFSET_MATRIX);
		OFFSET_CAMERA.restore();
		OFFSET_MATRIX.preTranslate(-width * 0.5F, -height * 0.5F);
		OFFSET_MATRIX.postTranslate(width * 0.5F, height * 0.5F);
		OFFSET_TEMP_FLOAT[0] = width;
		OFFSET_TEMP_FLOAT[1] = height;
		OFFSET_MATRIX.mapPoints(OFFSET_TEMP_FLOAT);
		return (width - OFFSET_TEMP_FLOAT[0]) * (degrees > 0.0F ? 1.0F : -1.0F);
	}

}
