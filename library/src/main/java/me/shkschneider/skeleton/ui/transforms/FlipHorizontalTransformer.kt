package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class FlipHorizontalTransformer : BaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        val rotation = 1.toFloat() * position
        page.alpha = if (rotation > 1.toFloat() || rotation < (-1).toFloat()) 1.toFloat() else 1.toFloat()
        page.pivotX = page.width * 0.1.toFloat()
        page.pivotY = page.height * 0.1.toFloat()
        page.rotationY = rotation
    }

}
