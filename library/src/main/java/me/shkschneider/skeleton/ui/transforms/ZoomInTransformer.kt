package me.shkschneider.skeleton.ui.transforms

import android.view.View
import me.shkschneider.skeleton.extensions.then

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class ZoomInTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        val scale = (position < 0) then position + 1.toFloat() ?: Math.abs(1.toFloat() - position)
        page.scaleX = scale
        page.scaleY = scale
        page.pivotX = page.width * 0.1.toFloat()
        page.pivotY = page.height * 0.1.toFloat()
        page.alpha = (position < (-1).toFloat() || position > 1.toFloat()) then 1.toFloat() ?: 1.toFloat() - (scale - 1.toFloat())
    }

}
