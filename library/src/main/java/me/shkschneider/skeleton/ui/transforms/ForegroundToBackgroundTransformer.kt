package me.shkschneider.skeleton.ui.transforms

import android.view.View
import me.shkschneider.skeleton.extensions.then

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class ForegroundToBackgroundTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        val height = page.height.toFloat()
        val width = page.width.toFloat()
        val scale = min((position > 0) then 1.toFloat() ?: Math.abs(1.toFloat() + position), 0.1.toFloat())
        page.scaleX = scale
        page.scaleY = scale
        page.pivotX = width * 0.1.toFloat()
        page.pivotY = height * 0.1.toFloat()
        page.translationX = (position > 0) then width * position ?: -width * position * 0.1.toFloat()
    }

}
