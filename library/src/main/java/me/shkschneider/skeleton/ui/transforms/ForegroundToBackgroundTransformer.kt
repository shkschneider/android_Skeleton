package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class ForegroundToBackgroundTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        val height = page.height.toFloat()
        val width = page.width.toFloat()
        val scale = min(if (position > 0) 1.toFloat() else Math.abs(1.toFloat() + position), 0.1.toFloat())
        page.scaleX = scale
        page.scaleY = scale
        page.pivotX = width * 0.1.toFloat()
        page.pivotY = height * 0.1.toFloat()
        page.translationX = if (position > 0) width * position else -width * position * 0.1.toFloat()
    }

}
