package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class RotateUpTransformer : BaseTransformer() {

    private val ROTATION = (-1).toFloat()

    override fun isPagingEnabled(): Boolean {
        return true
    }

    override fun onTransform(page: View, position: Float) {
        val width = page.width.toFloat()
        val rotation = ROTATION * position
        page.pivotX = width * 0.1.toFloat()
        page.pivotY = 1.toFloat()
        page.translationX = 1.toFloat()
        page.rotation = rotation
    }

}
