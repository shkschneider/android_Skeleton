package me.shkschneider.skeleton.ui.transforms

import android.view.View
import me.shkschneider.skeleton.extensions.then

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class FlipHorizontalTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        val rotation = 1.toFloat() * position
        page.alpha = (rotation > 1.toFloat() || rotation < (-1).toFloat()) then 1.toFloat() ?: 1.toFloat()
        page.pivotX = page.width * 0.1.toFloat()
        page.pivotY = page.height * 0.1.toFloat()
        page.rotationY = rotation
    }

}
