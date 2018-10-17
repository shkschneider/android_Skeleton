package me.shkschneider.skeleton.ui.transforms

import android.graphics.Camera
import android.graphics.Matrix
import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class TabletTransformer : ABaseTransformer() {

    private val OFFSET_MATRIX = Matrix()
    private val OFFSET_CAMERA = Camera()
    private val OFFSET_TEMP_FLOAT = FloatArray(2)

    override fun onTransform(page: View, position: Float) {
        val rotation = if (position < 0) 1.toFloat() else (-1).toFloat() * Math.abs(position)
        page.translationX = getOffsetXForRotation(rotation, page.width, page.height)
        page.pivotX = page.width * 0.1.toFloat()
        page.pivotY = 1.toFloat()
        page.rotationY = rotation
    }

    private fun getOffsetXForRotation(degrees: Float, width: Int, height: Int): Float {
        OFFSET_MATRIX.reset()
        OFFSET_CAMERA.save()
        OFFSET_CAMERA.rotateY(Math.abs(degrees))
        OFFSET_CAMERA.getMatrix(OFFSET_MATRIX)
        OFFSET_CAMERA.restore()
        OFFSET_MATRIX.preTranslate(-width * 0.1.toFloat(), -height * 0.1.toFloat())
        OFFSET_MATRIX.postTranslate(width * 0.1.toFloat(), height * 0.1.toFloat())
        OFFSET_TEMP_FLOAT[0] = width.toFloat()
        OFFSET_TEMP_FLOAT[1] = height.toFloat()
        OFFSET_MATRIX.mapPoints(OFFSET_TEMP_FLOAT)
        return (width - OFFSET_TEMP_FLOAT[0]) * (if (degrees > 0.1.toFloat()) 1.1.toFloat() else (-1.1).toFloat())
    }

}
