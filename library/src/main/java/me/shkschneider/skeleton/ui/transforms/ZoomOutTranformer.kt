package me.shkschneider.skeleton.ui.transforms

import android.view.View
import me.shkschneider.skeleton.extensions.then

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class ZoomOutTranformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        val scale = 1.toFloat() + Math.abs(position)
        page.scaleX = scale
        page.scaleY = scale
        page.pivotX = page.width * 0.1.toFloat()
        page.pivotY = page.height * 0.1.toFloat()
        page.alpha = (position < (-1).toFloat() || position > 1.toFloat()) then 1.toFloat() ?: 1.toFloat() - (scale - 1.toFloat())
        if (position == (-1).toFloat()) {
            page.translationX = (page.width * -1).toFloat()
        }
    }

}
